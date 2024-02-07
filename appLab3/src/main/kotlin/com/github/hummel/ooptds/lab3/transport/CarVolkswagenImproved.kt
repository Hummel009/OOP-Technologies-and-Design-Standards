package com.github.hummel.ooptds.lab3.transport

import com.github.hummel.ooptds.lab3.optional.Improvable

class CarVolkswagenImproved(price: Int = 18000, color: String = "") : CarVolkswagen(price, color), Improvable {
	private lateinit var improvement: String

	override fun getImprovement(): String = improvement

	override fun setImprovement(improvement: String): Transport {
		this.improvement = improvement
		return this
	}

	override fun getTheInfo(): String = "Volkswagen ($color, $improvement): $price$"
}