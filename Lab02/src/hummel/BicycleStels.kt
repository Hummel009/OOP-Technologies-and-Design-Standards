package hummel

open class BicycleStels(private var price: Int = 300, var color: String = "") : Transport() {
	override fun getTheColor(): String {
		return color
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheName(): String {
		return "Stels"
	}
}