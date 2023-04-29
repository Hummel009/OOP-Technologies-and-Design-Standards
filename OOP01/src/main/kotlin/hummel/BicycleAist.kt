package hummel

class BicycleAist(private var color: String) : BicycleTransport("Aist") {
	private var price = 100

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
