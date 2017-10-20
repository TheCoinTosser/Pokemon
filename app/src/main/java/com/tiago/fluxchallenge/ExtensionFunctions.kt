package com.tiago.fluxchallenge

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by tiago on 19/10/17.
 */
fun SwipeRefreshLayout.showLoadingIcon(){ this.isRefreshing = true }
fun SwipeRefreshLayout.hideLoadingIcon(){ this.isRefreshing = false }
fun SwipeRefreshLayout.removeListener(){ this.setOnRefreshListener(null) }

fun View.showOrHide(conditionToShow: Boolean) = if(conditionToShow) show() else hide()
fun View.show(){ visibility = View.VISIBLE }
fun View.hide(){ visibility = View.GONE }

fun <T> Collection<T>?.isNullOrEmpty() = (this == null || isEmpty())
fun <T> List<T>?.emptyFallback(): List<T> = this ?: listOf()

fun TextView.setTextAndVisibility(@StringRes textId: Int,
								  string: String? = null,
								  long: Long? = null){

	if(string != null){
		setTextAndVisibility(context.getString(textId, string))
	}

	if(long != null){
		setTextAndVisibility(context.getString(textId, long))
	}
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

		private var loading = true
		private var previousTotal = 0
		private val visibleThreshold = 5

		override fun onScrollStateChanged(recyclerView: RecyclerView,
										  newState: Int) {

			super.onScrollStateChanged(recyclerView, newState)

			with(layoutManager as LinearLayoutManager){

				val visibleItemCount = childCount
				val totalItemCount = itemCount
				val firstVisibleItem = findFirstVisibleItemPosition()

				if (loading && totalItemCount > previousTotal){

					loading = false
					previousTotal = totalItemCount
				}

				if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){

					onScrolledToEnd()
					loading = true
				}
			}
		}
	})
}

/**
 * This allows us to loop through a mutable collection, perform an operation on each item, and then
 * safely remove it from the collection
 */
fun <T> MutableCollection<T>.removeAfter(action: (T) -> Unit){

	val iter = this.iterator()
	while (iter.hasNext()) {
		val item = iter.next()
		action(item)
		iter.remove()
	}
}

fun ImageView.loadImage(imageUrl: String?,
						@DrawableRes errorDrawableResId: Int? = null){

	val glideRequest = GlideApp.with(context).load(imageUrl)
	if(errorDrawableResId != null){
		glideRequest.error(errorDrawableResId)
	}

	glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
	glideRequest.transition(DrawableTransitionOptions.withCrossFade())
	glideRequest.into(this)
}
