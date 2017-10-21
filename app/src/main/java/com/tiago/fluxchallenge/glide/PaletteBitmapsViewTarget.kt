package com.tiago.fluxchallenge.glide

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.tiago.fluxchallenge.R
import com.tiago.fluxchallenge.fadeIn
import com.tiago.fluxchallenge.extensions.getColorCompat

/**
 * Created by tiago on 20/10/17.
 */
class PaletteBitmapsViewTarget(private val imageView: ImageView,
							   private val textView: TextView?) : ImageViewTarget<PaletteBitmap>(imageView) {

	override fun setResource(resource: PaletteBitmap?) {

		resource?.let {

			with(imageView.context){

				imageView.setImageBitmap(it.bitmap)
				textView?.setBackgroundColor(it.palette.lightMutedSwatch?.rgb ?: getColorCompat(R.color.semitransparent))
				textView?.setTextColor(it.palette.lightMutedSwatch?.bodyTextColor ?: getColorCompat(R.color.pokemon_title))

				fadeIn(imageView, textView)
			}
		}
	}

	override fun onResourceReady(resource: PaletteBitmap?,
								 transition: Transition<in PaletteBitmap>?) {

		setResource(resource)
	}
}