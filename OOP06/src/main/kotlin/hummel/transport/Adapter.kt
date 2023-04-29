package hummel.transport

import hummel.special.Visitor

class Adapter(private val car: CarPeugeot) : CarBasic(car.price, car.color, car.name) {
	override fun getTheInfo(): String {
		return car.getInfo()
	}

	override fun accept(visitor: Visitor) {
	}
}