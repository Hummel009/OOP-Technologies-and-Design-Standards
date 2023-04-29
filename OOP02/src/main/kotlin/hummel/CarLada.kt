package hummel

open class CarLada(private var price: Int = 6000, var color: String = "") : Transport() {
	override fun getTheColor(): String {
		return color
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheName(): String {
		return "Lada"
	}
}