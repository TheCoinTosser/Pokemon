package com.tiago.fluxchallenge.glide

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by tiago on 20/10/17.
 */
class PaletteBitmapsViewTarget(private val imageView: ImageView,
							   private val textView: TextView) : ImageViewTarget<PaletteBitmap>(imageView) {

	override fun setResource(resource: PaletteBitmap?) {

		resource?.let {

			with(imageView.context){

				imageView.setImageBitmap(it.bitmap)

				it.palette.lightMutedSwatch?.rgb?.let {
					textView.setBackgroundColor(it)
				}

				it.palette.lightMutedSwatch?.bodyTextColor?.let {
					textView.setTextColor(it)
				}
			}
		}
	}

	override fun onResourceReady(resource: PaletteBitmap,
								 transition: Transition<in PaletteBitmap>?) {

		setResource(resource)
	}
}