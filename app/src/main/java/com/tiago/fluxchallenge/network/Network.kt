package com.tiago.fluxchallenge.network

import com.tiago.fluxchallenge.network.IPokemonAPI.Companion.IMAGE_URL_TEMPLATE
import com.tiago.fluxchallenge.network.IPokemonAPI.Companion.POKEMON_BASE_URL
import com.tiago.fluxchallenge.network.IPokemonAPI.Companion.TIMEOUT_IN_SECONDS
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by tiago on 19/10/17.
 *
 * In the real world I would probably use a dependency injection framework instead of defining a
 * singleton directly. That way we could @Inject it wherever appropriate and use its interface
 * instead. I think adding a DI framework is overkill for this app, so I am taking the liberty to
 * define it as a singleton manually.
 */
object NetworkImpl : INetwork {

	private val pokemonAPI: IPokemonAPI

	init {
		val okHttpClient = OkHttpClient.Builder()
				.connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
				.readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).build()

		val retrofit = Retrofit.Builder()
				.baseUrl(POKEMON_BASE_URL)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()

		pokemonAPI = retrofit.create(IPokemonAPI::class.java)
	}

	override fun getPokemonResult(offset: Int) = pokemonAPI.getPokemonResult(offset)

	override fun getPokemonDetails(pokemonId: Int) = pokemonAPI.getPokemonDetails(pokemonId)

	override fun getImageUrl(pokemonId: Int?) = String.format(IMAGE_URL_TEMPLATE, pokemonId)
}
