package com.tiago.fluxchallenge.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by tiago on 19/10/17.
 *
 * In the real world I would probably use a dependency injection framework instead of defining a
 * singleton directly. That way we could @Inject it wherever appropriate and use its interface
 * instead. I think adding a DI framework is overkill for this app, so I am taking the liberty to
 * define it as a singleton and use it directly instead.
 */
object NetworkImpl : INetwork {

	private val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other-sprites/official-artwork/%d.png"
	private val pokemonAPI: IPokemonAPI

	init {
		val retrofit = Retrofit.Builder()
				.baseUrl("https://pokeapi.co/")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()

		pokemonAPI = retrofit.create(IPokemonAPI::class.java)
	}

	override fun getPokemonResult(offset: Int) = pokemonAPI.getPokemonResult(offset)

	override fun getPokemonDetails(pokemonId: Int) = pokemonAPI.getPokemonDetails(pokemonId)

	override fun getImageUrl(pokemonId: Int) = String.format(imageUrl, pokemonId)
}
