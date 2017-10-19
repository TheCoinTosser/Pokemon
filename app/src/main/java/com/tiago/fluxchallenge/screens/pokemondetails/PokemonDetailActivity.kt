package com.tiago.fluxchallenge.screens.pokemondetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tiago.fluxchallenge.R
import com.tiago.fluxchallenge.screens.pokemonlist.PokemonListActivity
import kotlinx.android.synthetic.main.activity_pokemon_detail.*

/**
 * An activity representing a single Pokemon detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [PokemonListActivity].
 */
class PokemonDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pokemon_detail)
		setSupportActionBar(detail_toolbar)

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			val arguments = Bundle()
			arguments.putString(PokemonDetailFragment.ITEM,
					intent.getStringExtra(PokemonDetailFragment.ITEM))
			val fragment = PokemonDetailFragment()
			fragment.arguments = arguments
			supportFragmentManager.beginTransaction()
					.add(R.id.pokemon_detail_container, fragment)
					.commit()
		}
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
