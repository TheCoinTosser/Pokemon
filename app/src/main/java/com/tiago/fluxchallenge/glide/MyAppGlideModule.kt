package com.tiago.fluxchallenge.glide

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by tiago on 19/10/17.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule(){

	override fun registerComponents(context: Context,
									glide: Glide,
									registry: Registry) {

		registry.register(Bitmap::class.java,
						  PaletteBitmap::class.java,
						  PaletteBitmapTranscoder(glide))
	}
}