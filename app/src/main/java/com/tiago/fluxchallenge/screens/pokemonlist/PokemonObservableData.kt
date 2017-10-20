package com.tiago.fluxchallenge.screens.pokemonlist

import com.tiago.fluxchallenge.network.models.Result

/**
 * Created by tiago on 19/10/17.
 */
class PokemonObservableData private constructor(val results: List<Result>,
												val error: Boolean) {

	companion object {

		fun buildItems(results: List<Result>): PokemonObservableData {
			return PokemonObservableData(results, false)
		}

		fun buildError(): PokemonObservableData {
			return PokemonObservableData(listOf(), true)
		}
	}
}