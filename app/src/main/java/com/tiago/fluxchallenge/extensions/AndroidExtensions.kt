package com.tiago.fluxchallenge.extensions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tiago.fluxchallenge.glide.GlideApp
import com.tiago.fluxchallenge.glide.PaletteBitmap
import com.tiago.fluxchallenge.glide.PaletteBitmapsViewTarget

/**
 * Created by tiago on 21/10/17.
 */
fun SwipeRefreshLayout.showLoadingIcon(){ this.isRefreshing = true }
fun SwipeRefreshLayout.hideLoadingIcon(){ this.isRefreshing = false }
fun SwipeRefreshLayout.enable(){ this.isEnabled = true }
fun SwipeRefreshLayout.disable(){ this.isEnabled = false }

fun View.showOrHide(conditionToShow: Boolean) = if(conditionToShow) show() else hide()
fun View.show(){ visibility = View.VISIBLE }
fun View.hide(){ visibility = View.GONE }

fun <T : RecyclerView.ViewHolder> RecyclerView.Adapter<T>.isEmpty() = (itemCount == 0)

fun Context.getColorCompat(@ColorRes colorResId: Int): Int{

	return ContextCompat.getColor(this, colorResId)
}

fun TextView.setTextAndVisibility(@StringRes textId: Int,
								  string: String? = null){

	setTextAndVisibility( context.getStringOrNull(textId, string) )
}

fun TextView.setTextAndVisibility(@StringRes textId: Int,
								  int: Int? = null){

	setTextAndVisibility( context.getStringOrNull(textId, int) )
}

fun TextView.setTextAndVisibility(@StringRes textId: Int,
								  float: Float? = null){

	setTextAndVisibility( context.getStringOrNull(textId, float) )
}

private fun Context.getStringOrNull(@StringRes textId: Int,
									stringIntLongFloat: Any? = null) = when(stringIntLongFloat){
	null -> null
	else -> getString(textId, stringIntLongFloat)
}

fun TextView.setTextAndVisibility(text: String?){

	if(text.isNullOrEmpty()){
		hide()

	}else{
		show()
		this.text = text
	}
}

/**
 * WARNING: This assumes the layout manager is a LinearLayoutManager
 */
fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit){

	this.addOnScrollListener(object: RecyclerView.OnScrollListener(){

		private val VISIBLE_THRESHOLD = 5

		private var loading = true
		private var previousTotal = 0

		override fun onScrollStateChanged(recyclerView: RecyclerView,
										  newState: Int) {

			with(layoutManager as LinearLayoutManager){

				val visibleItemCount = childCount
				val totalItemCount = itemCount
				val firstVisibleItem = findFirstVisibleItemPosition()

				if (loading && totalItemCount > previousTotal){

					loading = false
					previousTotal = totalItemCount
				}

				if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)){

					onScrolledToEnd()
					loading = true
				}
			}
		}
	})
}

fun ImageView.loadImage(imageUrl: String?,
						@DrawableRes errorDrawableResId: Int? = null,
						animate: Boolean = false){

	val glideRequest = GlideApp.with(context).load(imageUrl)

	if(animate){
		glideRequest.transition(DrawableTransitionOptions.withCrossFade())
	}

	glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)

	if(errorDrawableResId != null){
		glideRequest.error(errorDrawableResId)
	}

	glideRequest.into(this)
}

fun ImageView.loadImageWithPalette(imageUrl: String?,
								   @DrawableRes errorDrawableResId: Int? = null,
								   textView: TextView,
								   @ColorRes textViewBackgroundColorIdFallback: Int,
								   @ColorRes textViewTitleColorIdFallback: Int){

	textView.setBackgroundColor( context.getColorCompat(textViewBackgroundColorIdFallback) )
	textView.setTextColor( context.getColorCompat(textViewTitleColorIdFallback) )

	val glideRequest = GlideApp.with(context)
			.`as`(PaletteBitmap::class.java)
			.diskCacheStrategy(DiskCacheStrategy.DATA)
			.load(imageUrl)

	if(errorDrawableResId != null){
		glideRequest.error(errorDrawableResId)
	}

	glideRequest.into(PaletteBitmapsViewTarget(this, textView))
}

fun View.animateToBackgroundColor(@ColorInt colorInt: Int){

	background.let {

		if(it is ColorDrawable){

			animateColor(
					it.color,
					colorInt,
					f = { interpolatedColor -> setBackgroundColor(interpolatedColor) }
			)
		}
	}
}

fun TextView.animateToTextColor(@ColorInt colorInt: Int){

	animateColor(
			currentTextColor,
			colorInt,
			f = { interpolatedColor -> setTextColor(interpolatedColor) }
	)
}

private fun animateColor(@ColorInt currentRGBColor: Int,
						 @ColorInt targetRGBColor: Int,
						 f: (Int) -> Unit){

	val animator = ValueAnimator()
	animator.setIntValues(currentRGBColor, targetRGBColor)
	animator.setEvaluator(ArgbEvaluator())
	animator.addUpdateListener {
		valueAnimator -> f(valueAnimator.animatedValue as Int)
	}
	animator.start()
}