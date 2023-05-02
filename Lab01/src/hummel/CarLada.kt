package hummel

class CarLada(private var color: String) : CarTransport("Lada") {
	private var price = 5000

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