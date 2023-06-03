package hummel.transport

open class CarVolkswagen(private var price: Int = 18000, var color: String = "") : Transport(){
	override fun getTheColor(): String {
		return color
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheName(): String {
		return "Volkswagen"
	}
}