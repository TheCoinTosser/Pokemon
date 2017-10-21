package com.tiago.fluxchallenge.screens.pokemonlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import com.tiago.fluxchallenge.*
import com.tiago.fluxchallenge.extensions.*
import com.tiago.fluxchallenge.network.NetworkImpl
import com.tiago.fluxchallenge.network.models.Result
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.android.synthetic.main.no_item.*
import kotlinx.android.synthetic.main.pokemon_list.*
import kotlinx.android.synthetic.main.pokemon_list_common.*

class PokemonListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 */
	private val twoPane by lazy { pokemon_detail_container != null }
	private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelPokemonList::class.java) }


	override fun onRefresh() {
		fetch(forceNetwork = true)
	}

	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pokemon_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		swipeRefreshLayout.setOnRefreshListener(this)

		recyclerView.addOnScrolledToEnd {

			fetch(forceNetwork = true)
		}

		viewModel.observePokemonData(this, Observer {

			swipeRefreshLayout.hideLoadingIcon()

			it?.let {

				if(it.error){
					Snackbar.make(root, R.string.failed_to_fetch_from_server, Snackbar.LENGTH_LONG).show()
					swipeRefreshLayout.enable()

				}else{
					swipeRefreshLayout.disable()
				}

				populateData(it.results.emptyFallback())
				textViewNoItems.showOrHide(conditionToShow = recyclerView.adapter.isEmpty())
			}
		})

		fetch()
	}

	private fun populateData(results: List<Result>){

		if(recyclerView.adapter == null){
			recyclerView.adapter = PokemonListAdapter(NetworkImpl, this, results.toMutableList(), twoPane)

		}else{
			(recyclerView.adapter as PokemonListAdapter).populateData(results)
		}
	}

	private fun fetch(forceNetwork: Boolean = false){

		viewModel.fetch(forceNetwork)
		swipeRefreshLayout.showLoadingIcon()
	}
}
