package com.tiago.fluxchallenge.screens.pokemonlist

import android.arch.lifecycle.LiveData
import com.tiago.fluxchallenge.extensions.emptyFallback
import com.tiago.fluxchallenge.extensions.removeAfter
import com.tiago.fluxchallenge.network.IPokemonAPI
import com.tiago.fluxchallenge.network.NetworkImpl
import com.tiago.fluxchallenge.network.models.PokemonResult
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by tiago on 19/10/17.
 */
class PokemonLiveData : LiveData<PokemonObservableData>() {

	private val disposables: MutableList<Disposable> = ArrayList()

	private var pokemonObservableData: PokemonObservableData? = null
	private var offset = 0

	fun fetch(forceNetwork: Boolean){

		pokemonObservableData.let {

			if(forceNetwork || it == null){
				fetchFromNetwork()

			}else{
				deliver(it)
			}
		}
	}

	override fun onInactive() = disposables.removeAfter { it.dispose() }

	private fun fetchFromNetwork() {

		NetworkImpl.getPokemonResult(offset)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : SingleObserver<PokemonResult> {

					override fun onSubscribe(d: Disposable) {

						disposables.add(d)
					}

					override fun onSuccess(pokemonResult: PokemonResult) {

						offset += IPokemonAPI.LIMIT_DEFAULT

						val mutableList = pokemonObservableData?.results?.toMutableList() ?: mutableListOf()
						mutableList.addAll(pokemonResult.results.emptyFallback())

						PokemonObservableData.buildItems(mutableList.toList()).let {

							pokemonObservableData = it
							deliver(it)
						}
					}

					override fun onError(e: Throwable) {

						// Unfortunately LiveData does not have a built-in way to return an error
						deliver( PokemonObservableData.buildError() )
					}
				})
	}

	private fun deliver(pokemonObservableData: PokemonObservableData){

		value = pokemonObservableData
	}
}