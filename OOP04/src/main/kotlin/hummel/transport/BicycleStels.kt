package hummel.transport

import hummel.optional.Editable

open class BicycleStels(var price: Int = 300, var color: String = "") : Transport(), Editable {
	override fun getTheColor(): String {
		return color
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheName(): String {
		return "Stels"
	}

	override fun setThePrice(price: Int) {
		this.price = price
	}

	override fun setTheColor(color: String) {
		this.color = color
	}
}