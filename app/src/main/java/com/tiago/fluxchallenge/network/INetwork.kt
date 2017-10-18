package com.tiago.fluxchallenge.network

import com.tiago.fluxchallenge.network.models.PokemonDetails
import com.tiago.fluxchallenge.network.models.PokemonResult
import io.reactivex.Single

/**
 * Created by tiago on 19/10/17.
 */
interface INetwork{

	fun getPokemonResult(offset: Int = 0): Single<PokemonResult>

	fun getPokemonDetails(pokemonId: Int): Single<PokemonDetails>

	fun getImageUrl(pokemonId: Int): String
}
