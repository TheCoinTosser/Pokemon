package com.tiago.fluxchallenge.screens.pokemondetails

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

/**
 * Created by tiago on 20/10/17.
 */
class ViewModelPokemonDetails(application: Application): AndroidViewModel(application) {

	private val pokemonDetailsLiveData = PokemonDetailsLiveData()

	fun observePokemonData(owner: LifecycleOwner,
						   observer: Observer<PokemonDetailsObservableData>){

		return pokemonDetailsLiveData.observe(owner, observer)
	}

	fun fetch(pokemonId: Int){

		pokemonDetailsLiveData.fetch(pokemonId)
	}
}