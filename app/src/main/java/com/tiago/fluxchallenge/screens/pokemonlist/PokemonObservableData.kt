package com.tiago.fluxchallenge.screens.pokemonlist

import com.tiago.fluxchallenge.network.models.PokemonResult

/**
 * Created by tiago on 19/10/17.
 */
class PokemonObservableData private constructor(val pokemonResult: PokemonResult?,
												val error: Boolean) {

	companion object {

		fun buildItems(pokemonResult: PokemonResult): PokemonObservableData {
			return PokemonObservableData(pokemonResult, false)
		}

		fun buildError(): PokemonObservableData {
			return PokemonObservableData(PokemonResult(), true)
		}
	}
}