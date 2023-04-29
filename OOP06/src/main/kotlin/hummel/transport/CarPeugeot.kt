package hummel.transport

class CarPeugeot(var price: Int = 6000, var color: String = "Red", var name: String = "Peugeot") {
	fun getInfo(): String {
		return "Peugeot ($color): $price$"
	}
}