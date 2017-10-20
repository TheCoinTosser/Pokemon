package com.tiago.fluxchallenge.glide

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder

/**
 * Created by tiago on 20/10/17.
 *
 * Based on: https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_1013_palette/PaletteBitmapTranscoder.java
 */
class PaletteBitmapTranscoder(val glide: Glide) : ResourceTranscoder<Bitmap, PaletteBitmap> {

	override fun transcode(toTranscode: Resource<Bitmap>,
						   options: Options?): Resource<PaletteBitmap> {

		val bitmap = toTranscode.get()
		val palette = Palette.Builder(bitmap).generate()
		val paletteBitmap = PaletteBitmap(bitmap, palette)
		return PaletteBitmapResource(paletteBitmap, glide.bitmapPool)
	}
}