package com.tiago.fluxchallenge.network

import com.tiago.fluxchallenge.network.models.PokemonDetails
import com.tiago.fluxchallenge.network.models.PokemonResult
import io.reactivex.Single

/**
 * Created by tiago on 19/10/17.
 */
interface INetwork{

	companion object {
		const val POKEMON_BASE_URL = "https://pokeapi.co/api/v2/pokemon/"
		const val IMAGE_URL_TEMPLATE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other-sprites/official-artwork/%d.png"
	}

	fun getPokemonResult(offset: Int = 0): Single<PokemonResult>

	fun getPokemonDetails(pokemonId: Int): Single<PokemonDetails>

	fun getImageUrl(pokemonId: Int?): String
}
