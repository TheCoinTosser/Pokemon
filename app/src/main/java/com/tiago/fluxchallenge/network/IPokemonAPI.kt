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

	@GET("api/v2/pokemon")
	fun getPokemonResult(@Query("offset") offset: Int): Single<PokemonResult>

	@GET("api/v2/pokemon/{pokemonId}")
	fun getPokemonDetails(@Path("pokemonId") pokemonId: Int): Single<PokemonDetails>
}
