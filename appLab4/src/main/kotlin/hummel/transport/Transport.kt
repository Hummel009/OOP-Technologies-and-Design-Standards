package hummel.transport

open class Transport(var price: Int = 0, var color: String = "Color", var name: String = "Name") {
	open fun getTheInfo(): String = "$name ($color): $price$"
}