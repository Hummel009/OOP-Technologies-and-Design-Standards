package hummel

class CarVolkswagen(private var color: String) : CarTransport("Volkswagen") {
	private var price = 18000

	constructor(color: String, i: Int) : this(color) {
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