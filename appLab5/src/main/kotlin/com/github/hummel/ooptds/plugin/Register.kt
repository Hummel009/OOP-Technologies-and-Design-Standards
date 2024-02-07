package com.github.hummel.ooptds.plugin

class Register(numbers: IntArray, initial: String) {
	private val powers: IntArray = numbers.copyOf()
	private val bufNow: IntArray = IntArray(powers.maxOrNull() ?: 0)
	private val xorCells: IntArray = IntArray(powers.size)

	init {
		var i = 0
		while (i < bufNow.size && i < initial.length) {
			bufNow[bufNow.size - 1 - i] = initial[i].digitToInt()
			i++
		}
		while (i < bufNow.size) {
			bufNow[bufNow.size - 1 - i] = 1
			i++
		}
	}

	fun generateKeyBit(): Int {
		val keyBit = bufNow[bufNow.size - 1]

		powers.forEachIndexed { i, power -> xorCells[i] = bufNow[power - 1] }

		for (i in bufNow.size - 1 downTo 1) {
			bufNow[i] = bufNow[i - 1]
		}

		bufNow[0] = xorCells[0]

		for (i in 1 until xorCells.size) {
			bufNow[0] = bufNow[0] xor xorCells[i]
		}

		return keyBit
	}
}