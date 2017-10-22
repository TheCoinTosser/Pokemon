package com.tiago.fluxchallenge.network

import com.tiago.fluxchallenge.network.models.PokemonDetails
import com.tiago.fluxchallenge.network.models.PokemonResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by tiago on 19/10/17.
 */
internal interface IPokemonAPI {

	companion object {
		const val TIMEOUT_IN_SECONDS = 30L
		const val LIMIT_DEFAULT = 20
		const val POKEMON_BASE_URL = "https://pokeapi.co/api/v2/pokemon/"
		const val IMAGE_URL_TEMPLATE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other-sprites/official-artwork/%d.png"
	}

	@GET(".")
	fun getPokemonResult(@Query("offset") offset: Int,
						 @Query("limit") limit: Int = LIMIT_DEFAULT): Single<PokemonResult>

	@GET("{pokemonId}")
	fun getPokemonDetails(@Path("pokemonId") pokemonId: Int): Single<PokemonDetails>
}
