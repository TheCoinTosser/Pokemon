package com.tiago.fluxchallenge.network.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Created by tiago on 19/10/17.
 */
class ResultTests {

	@Test
	@Throws(Exception::class)
	fun testExtractPokemonIdFunction() {

		val result1 = Result("https://pokeapi.co/api/v2/pokemon/1/","")
		assertEquals(1, result1.extractPokemonId())

		val resultTwoDigits = Result("https://pokeapi.co/api/v2/pokemon/54/","")
		assertEquals(54, resultTwoDigits.extractPokemonId())

		val resultNull = Result("https://pokeapi.co/api/v2/pokemon//","")
		assertNull(resultNull.extractPokemonId())

		val resultNull2 = Result("https://pokeapi.co/api/v2/pokemon/","")
		assertNull(resultNull2.extractPokemonId())
	}
}