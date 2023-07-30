package hummel.transport

class CarVolkswagen(private var color: String) : CarTransport("Volkswagen") {
	private var price = 18000

	constructor(i: Int, color: String) : this(color) {
		this.price = i
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheColor(): String {
		return color
	}

	override fun getTheInfo(): String {
		return getTheName() + " (" + getTheColor() + "): " + getThePrice() + "$"
	}
}