package com.tiago.fluxchallenge.screens.pokemonlist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tiago.fluxchallenge.R
import com.tiago.fluxchallenge.loadImage
import com.tiago.fluxchallenge.network.INetwork
import com.tiago.fluxchallenge.network.models.Result
import com.tiago.fluxchallenge.screens.pokemondetails.PokemonDetailActivity
import com.tiago.fluxchallenge.screens.pokemondetails.PokemonDetailFragment
import com.tiago.fluxchallenge.setTextAndVisibility
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

/**
 * Created by tiago on 19/10/17.
 */
class PokemonListAdapter(private val network: INetwork,
						 private val parentActivity: AppCompatActivity,
						 private val results: MutableList<Result>,
						 private val twoPane: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

	private val onClickListener: View.OnClickListener

	init {
		onClickListener = View.OnClickListener { view ->

			val result = view.tag as Result
			val pokemonId = result.extractPokemonId() ?: PokemonDetailFragment.UNKNOWN_ID
			val pokemonName = result.name.capitalize()
			if (twoPane) {

				val fragment = PokemonDetailFragment().apply {
					arguments = Bundle()
					arguments.putInt(PokemonDetailFragment.POKEMON_ID, pokemonId)
					arguments.putString(PokemonDetailFragment.POKEMON_NAME, pokemonName)
				}
				parentActivity.supportFragmentManager
						.beginTransaction()
						.replace(R.id.pokemon_detail_container, fragment)
						.commit()
			} else {

				val intent = Intent(view.context, PokemonDetailActivity::class.java).apply {
					putExtra(PokemonDetailFragment.POKEMON_ID, pokemonId)
					putExtra(PokemonDetailFragment.POKEMON_NAME, pokemonName)
				}

				val options = ActivityOptionsCompat.makeSceneTransitionAnimation(parentActivity,
																				 view.imageViewMain,
																				 view.context.getString(R.string.transition_image))
				view.context.startActivity(intent, options.toBundle())
			}
		}
	}

	fun populateData(newResults: List<Result>){

		if(newResults.isNotEmpty()){

			val firstNewPosition = this.results.size

			this.results.clear()
			this.results.addAll(newResults)
			notifyItemRangeInserted(firstNewPosition, newResults.size)
		}
	}

	override fun getItemCount() = results.size

	override fun onCreateViewHolder(parent: ViewGroup,
									viewType: Int):  RecyclerView.ViewHolder {

		val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
		return ViewHolderPokemon(view)
	}

	override fun onBindViewHolder(viewHolderPokemon:  RecyclerView.ViewHolder,
								  position: Int) {

		val result = results[position]

		with(viewHolderPokemon.itemView){

			imageViewMain.loadImage(network.getImageUrl(result.extractPokemonId()),
									R.drawable.pokeball,
									textViewPokemonName)
			textViewPokemonName.setTextAndVisibility(result.name.capitalize())

			tag = result
			setOnClickListener(onClickListener)
		}
	}

	inner class ViewHolderPokemon(view: View) : RecyclerView.ViewHolder(view)
}