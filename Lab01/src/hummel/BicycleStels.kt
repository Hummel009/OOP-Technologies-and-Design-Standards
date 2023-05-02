package hummel

class BicycleStels(private var color: String) : BicycleTransport("Stels") {
	private var price = 300

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