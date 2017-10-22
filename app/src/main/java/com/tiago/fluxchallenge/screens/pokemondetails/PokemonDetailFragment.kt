package com.tiago.fluxchallenge.screens.pokemondetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tiago.fluxchallenge.R
import com.tiago.fluxchallenge.extensions.*
import kotlinx.android.synthetic.main.loading_pokeball.*
import kotlinx.android.synthetic.main.pokemon_detail.*

/**
 * A fragment representing a single Pokemon detail screen.
 * This fragment is either contained in a [com.tiago.fluxchallenge.screens.pokemonlist.PokemonListActivity]
 * in two-pane mode (on tablets) or a [PokemonDetailActivity]
 * on handsets.
 */
class PokemonDetailFragment : Fragment() {

	companion object {
		const val POKEMON_ID = "pokemon_id"
		const val POKEMON_NAME = "pokemon_name"
		const val UNKNOWN_ID = -1
	}

	private val pokemonId by lazy { arguments.getInt(POKEMON_ID) }
	private val viewModel by lazy { ViewModelProviders.of(activity).get(ViewModelPokemonDetails::class.java) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.observePokemonData(this, Observer {

			it?.let {

				loadingPokeball.hide()

				textViewError.showOrHide(conditionToShow = it.error)
				textViewHeight.setTextAndVisibility(R.string.height_format, it.pokemonDetails?.height?.inchesToCentimeters())
				textViewWeight.setTextAndVisibility(R.string.weight_format, it.pokemonDetails?.weight?.poundsToKilos())
				textViewBaseExperience.setTextAndVisibility(R.string.base_experience_format, it.pokemonDetails?.baseExperience)

				val typesFormatted = it.pokemonDetails
						?.types
						?.mapNotNull { typeItem -> typeItem.type?.name }
						?.toList()
						?.joinToString()
				textViewTypes.setTextAndVisibility(R.string.types_format, typesFormatted)

				val abilitiesFormatted = it.pokemonDetails
						?.abilities
						?.mapNotNull { abilityItem -> abilityItem.ability?.name }
						?.toList()
						?.joinToString()
				textViewAbilities.setTextAndVisibility(R.string.abilities_format, abilitiesFormatted)
			}
		})

		viewModel.fetch(pokemonId)
	}

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {

		return inflater.inflate(R.layout.pokemon_detail, container, false)
	}
}
