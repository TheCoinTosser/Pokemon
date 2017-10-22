package com.tiago.fluxchallenge.network

import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Test

/**
 * Created by tiago on 19/10/17.
 */
class NetworkTests{

	private val bulbasaurId = 1

	@Test
	@Throws(Exception::class)
	fun testGetPokemonCall() {

		val pokemonResult = NetworkImpl.getPokemonResult().blockingGet()
		assertNotNull(pokemonResult)

		val pokemonResultBatch2 = NetworkImpl.getPokemonResult(20).blockingGet()
		assertNotNull(pokemonResultBatch2)
		assertTrue( pokemonResultBatch2.next!!.contains("offset=40") )
	}

	@Test
	@Throws(Exception::class)
	fun testGetPokemonDetailsCall() {

		val pokemonDetails = NetworkImpl.getPokemonDetails(bulbasaurId).blockingGet()
		assertNotNull(pokemonDetails)
	}

	@Test
	@Throws(Exception::class)
	fun testGetImageUrlFunction(){

		val invalidImageUrl = NetworkImpl.getImageUrl(null)
		assertNotNull(invalidImageUrl)
	}
}