package hummel.transport

import hummel.optional.Improvable

class CarVolkswagenImproved(price: Int = 18000, color: String = "", private var improvement: String = "") : CarVolkswagen(price, color), Improvable {
	override fun getTheImprovement(): String {
		return improvement
	}

	override fun setTheImprovement(improvement: String) {
		this.improvement = improvement
	}

	override fun getTheInfo(): String {
		return "Volkswagen ($color, $improvement): $price$"
	}
}