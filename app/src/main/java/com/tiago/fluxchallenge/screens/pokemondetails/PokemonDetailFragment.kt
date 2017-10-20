package com.tiago.fluxchallenge.screens.pokemondetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tiago.fluxchallenge.R

/**
 * A fragment representing a single Pokemon detail screen.
 * This fragment is either contained in a [com.tiago.fluxchallenge.screens.pokemonlist.PokemonListActivity]
 * in two-pane mode (on tablets) or a [PokemonDetailActivity]
 * on handsets.
 */
class PokemonDetailFragment : Fragment() {

	companion object {
		const val ITEM = "item"
	}

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {

		val rootView = inflater.inflate(R.layout.pokemon_detail, container, false)

		//TODO

		return rootView
	}
}
