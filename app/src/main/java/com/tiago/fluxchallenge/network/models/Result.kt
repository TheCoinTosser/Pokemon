package com.tiago.fluxchallenge.network.models

import android.os.Parcel
import android.os.Parcelable
import com.tiago.fluxchallenge.network.IPokemonAPI.Companion.POKEMON_BASE_URL

/**
 * Created by tiago on 19/10/17.
 *
 * Unfortunately the @Parcelize annotation from Kotlin does not work on Jelly Bean yet... =(
 */
data class Result(private val url: String,
				  val name: String): Parcelable{

	fun extractPokemonId() = url.removePrefix(POKEMON_BASE_URL).removeSuffix("/").toIntOrNull()

	constructor(parcel: Parcel) : this(
			parcel.readString(),
			parcel.readString())

	override fun writeToParcel(dest: Parcel,
							   flags: Int) {
		dest.writeString(url)
		dest.writeString(name)
	}

	override fun describeContents() = 0

	companion object {

		@JvmField val CREATOR = object: Parcelable.Creator<Result> {

			override fun createFromParcel(parcel: Parcel): Result {
				return Result(parcel)
			}

			override fun newArray(size: Int): Array<Result?> {
				return arrayOfNulls(size)
			}
		}
	}
}