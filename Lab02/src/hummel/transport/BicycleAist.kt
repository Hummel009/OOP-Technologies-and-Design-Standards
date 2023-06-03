package hummel.transport

open class BicycleAist(private var price: Int = 200, var color: String = "") : Transport() {
	override fun getTheColor(): String {
		return color
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheName(): String {
		return "Aist"
	}
}
