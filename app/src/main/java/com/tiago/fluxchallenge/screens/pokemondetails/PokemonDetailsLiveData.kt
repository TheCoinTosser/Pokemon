package com.tiago.fluxchallenge.screens.pokemondetails

import android.arch.lifecycle.LiveData
import com.tiago.fluxchallenge.extensions.removeAfter
import com.tiago.fluxchallenge.network.NetworkImpl
import com.tiago.fluxchallenge.network.models.PokemonDetails
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by tiago on 20/10/17.
 */
class PokemonDetailsLiveData : LiveData<PokemonDetailsObservableData>() {

	private val disposables: MutableList<Disposable> = ArrayList()

	private var pokemonDetailsObservableData: PokemonDetailsObservableData? = null

	fun fetch(pokemonId: Int){

		pokemonDetailsObservableData.let {

			if(it == null){
				fetchFromNetwork(pokemonId)

			}else{
				deliver(it)
			}
		}
	}

	override fun onInactive() = disposables.removeAfter { it.dispose() }

	private fun fetchFromNetwork(pokemonId: Int) {

		NetworkImpl.getPokemonDetails(pokemonId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : SingleObserver<PokemonDetails> {

					override fun onSubscribe(d: Disposable) {

						disposables.add(d)
					}

					override fun onSuccess(pokemonDetails: PokemonDetails) {

						PokemonDetailsObservableData.buildItems(pokemonDetails).let {

							pokemonDetailsObservableData = it
							deliver(it)
						}
					}

					override fun onError(e: Throwable) {

						// Unfortunately LiveData does not have a built-in way to return an error
						deliver( PokemonDetailsObservableData.buildError() )
					}
				})
	}

	private fun deliver(pokemonDetailsObservableData: PokemonDetailsObservableData){

		value = pokemonDetailsObservableData
	}
}