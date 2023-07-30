package hummel.transport

import hummel.optional.Improvable

class CarVolkswagenImproved(price: Int = 18000, color: String = "") : CarVolkswagen(price, color), Improvable {
	private lateinit var improvement: String

	override fun getImprovement(): String {
		return improvement
	}

	override fun setImprovement(improvement: String): Transport {
		this.improvement = improvement
		return this
	}

	override fun getTheInfo(): String {
		return "Volkswagen ($color, $improvement): $price$"
	}
}