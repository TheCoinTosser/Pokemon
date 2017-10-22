package com.tiago.fluxchallenge.network.models

import com.google.gson.annotations.SerializedName

/**
 * Created by tiago on 19/10/17.
 */
class PokemonDetails(val name: String,
					 val weight: Float?,
					 val height: Float?,
					 @SerializedName("base_experience") val baseExperience: Int?,
					 val types: List<TypeItem>?,
					 val abilities: List<AbilityItem>?)