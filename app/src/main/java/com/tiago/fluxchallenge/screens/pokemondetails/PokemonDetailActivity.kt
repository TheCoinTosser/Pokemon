package com.tiago.fluxchallenge.screens.pokemondetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tiago.fluxchallenge.R
import com.tiago.fluxchallenge.loadImage
import com.tiago.fluxchallenge.network.INetwork
import com.tiago.fluxchallenge.network.NetworkImpl
import com.tiago.fluxchallenge.screens.pokemonlist.PokemonListActivity
import kotlinx.android.synthetic.main.activity_pokemon_detail.*

/**
 * An activity representing a single Pokemon detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [PokemonListActivity].
 */
class PokemonDetailActivity : AppCompatActivity() {

	private val pokemonId by lazy{ intent.getIntExtra(PokemonDetailFragment.POKEMON_ID, PokemonDetailFragment.UNKNOWN_ID) }
	private val pokemonName by lazy { intent.getStringExtra(PokemonDetailFragment.POKEMON_NAME) }
	private val network: INetwork by lazy{ NetworkImpl }

	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pokemon_detail)

		setSupportActionBar(toolbar)
		supportActionBar?.let {
			it.setDisplayHomeAsUpEnabled(true)
			it.title = pokemonName
		}

		if (savedInstanceState == null) {

			val arguments = Bundle()
			arguments.putInt(PokemonDetailFragment.POKEMON_ID, pokemonId)

			val fragment = PokemonDetailFragment()
			fragment.arguments = arguments
			supportFragmentManager
					.beginTransaction()
					.add(R.id.pokemon_detail_container, fragment)
					.commit()
		}

		imageViewHeader.loadImage(network.getImageUrl(pokemonId), R.drawable.pokeball)
	}

	override fun onOptionsItemSelected(item: MenuItem) =
			when (item.itemId) {
				android.R.id.home -> {
					super.onBackPressed()
					true
				}
				else -> super.onOptionsItemSelected(item)
			}
}
