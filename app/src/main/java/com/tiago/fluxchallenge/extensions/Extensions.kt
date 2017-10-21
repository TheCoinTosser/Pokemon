package com.tiago.fluxchallenge.extensions

/**
 * Created by tiago on 19/10/17.
 */
fun <T> List<T>?.emptyFallback(): List<T> = this ?: listOf()

/**
 * This allows us to loop through a mutable collection, perform an operation on each item, and then
 * safely remove it from the collection
 */
fun <T> MutableCollection<T>.removeAfter(action: (T) -> Unit){

	val iter = this.iterator()
	while (iter.hasNext()) {
		val item = iter.next()
		action(item)
		iter.remove()
	}
}
