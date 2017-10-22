package com.tiago.fluxchallenge.screens.pokemondetails

import com.tiago.fluxchallenge.network.models.PokemonDetails

/**
 * Created by tiago on 20/10/17.
 */
class PokemonDetailsObservableData private constructor(val pokemonDetails: PokemonDetails?,
													   val error: Boolean) {

	companion object {

		fun buildItems(pokemonDetails: PokemonDetails?): PokemonDetailsObservableData {
			return PokemonDetailsObservableData(pokemonDetails, false)
		}

		fun buildError(): PokemonDetailsObservableData {
			return PokemonDetailsObservableData(null, true)
		}
	}
}