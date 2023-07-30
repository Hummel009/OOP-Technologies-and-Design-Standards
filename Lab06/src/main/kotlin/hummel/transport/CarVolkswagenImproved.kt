package hummel.transport

import hummel.special.Improvable

class CarVolkswagenImproved(price: Int = 18000, color: String = "") : CarVolkswagen(price, color), Improvable {
	private lateinit var improvement: String

	override fun getImprovement(): String {
		return improvement
	}

	override fun setImprovement(improvement: String): Improvable {
		this.improvement = improvement
		return this
	}

	override fun getTheInfo(): String {
		return "Volkswagen ($color, $improvement): $price$"
	}
}