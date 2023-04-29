package hummel.transport

import hummel.optional.Improvable

class CarLadaImproved(price: Int = 6000, color: String = "", private var improvement: String = "") : CarLada(price, color), Improvable {
	override fun getTheImprovement(): String {
		return improvement
	}

	override fun setTheImprovement(improvement: String) {
		this.improvement = improvement
	}

	override fun getTheInfo(): String {
		return "Lada ($color, $improvement): $price$"
	}
}