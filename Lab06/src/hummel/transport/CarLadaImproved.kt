package hummel.transport

import hummel.special.Improvable

class CarLadaImproved(price: Int = 6000, color: String = "") : CarLada(price, color), Improvable {
	private lateinit var improvement: String

	override fun getImprovement(): String {
		return improvement
	}

	override fun setImprovement(improvement: String): Improvable {
		this.improvement = improvement
		return this
	}

	override fun getTheInfo(): String {
		return "Lada ($color, $improvement): $price$"
	}
}