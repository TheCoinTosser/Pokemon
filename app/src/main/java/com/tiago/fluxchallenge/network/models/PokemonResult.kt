package com.tiago.fluxchallenge.network.models

/**
 * Created by tiago on 19/10/17.
 */
class PokemonResult(val count: Int,
					val results: List<Result>?,
					val next: String)