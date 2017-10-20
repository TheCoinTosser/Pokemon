package com.tiago.fluxchallenge.glide

import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.util.Util

/**
 * Created by tiago on 20/10/17.
 *
 * Based on: https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_1013_palette/PaletteBitmapResource.java
 */
class PaletteBitmapResource(private val paletteBitmap: PaletteBitmap,
							private val bitmapPool: BitmapPool) : Resource<PaletteBitmap> {

	override fun get(): PaletteBitmap {
		return paletteBitmap
	}

	override fun getSize(): Int {
		return Util.getBitmapByteSize(paletteBitmap.bitmap)
	}

	override fun getResourceClass(): Class<PaletteBitmap> {
		return paletteBitmap.javaClass
	}

	override fun recycle() {
		bitmapPool.put(paletteBitmap.bitmap)
	}
}