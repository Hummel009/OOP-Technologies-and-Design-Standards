package hummel.transport

class BicycleStels(private var color: String) : BicycleTransport("Stels") {
	private var price = 300

	constructor(i: Int, color: String) : this(color) {
		this.price = i
	}

	override fun getThePrice(): Int = price

	override fun getTheColor(): String = color

	override fun getTheInfo(): String {
		return getTheName() + " (" + getTheColor() + "): " + getThePrice() + "$"
	}
}