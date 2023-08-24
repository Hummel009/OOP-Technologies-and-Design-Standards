package hummel.transport

class BicycleAist(private var color: String) : BicycleTransport("Aist") {
	private var price = 100

	constructor(i: Int, color: String) : this(color) {
		this.price = i
	}

	override fun getThePrice(): Int = price

	override fun getTheColor(): String = color

	override fun getTheInfo(): String {
		return getTheName() + " (" + getTheColor() + "): " + getThePrice() + "$"
	}
}
