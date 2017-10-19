package com.tiago.fluxchallenge.network.models

/**
 * Created by tiago on 19/10/17.
 */
class PokemonResult(val count: Int = 0,
					val results: List<Result>? = null,
					val next: String? = null)