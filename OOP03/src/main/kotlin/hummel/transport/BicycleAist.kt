package hummel.transport

import hummel.optional.Editable

open class BicycleAist(var price: Int = 200, var color: String = "") : Transport(), Editable {
	override fun getTheColor(): String {
		return color
	}

	override fun getThePrice(): Int {
		return price
	}

	override fun getTheName(): String {
		return "Aist"
	}

	override fun setThePrice(price: Int) {
		this.price = price
	}

	override fun setTheColor(color: String) {
		this.color = color
	}
}
