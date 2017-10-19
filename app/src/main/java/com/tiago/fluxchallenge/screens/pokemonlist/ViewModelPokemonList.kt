package com.tiago.fluxchallenge.screens.pokemonlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

/**
 * Created by tiago on 19/10/17.
 */
class ViewModelPokemonList(application: Application): AndroidViewModel(application) {

	private val pokemonLiveData = PokemonLiveData()

	fun observePokemonData(owner: LifecycleOwner,
						   observer: Observer<PokemonObservableData>){

		return pokemonLiveData.observe(owner, observer)
	}

	fun fetch(forceNetwork: Boolean = false){

		pokemonLiveData.fetch(forceNetwork)
	}
}