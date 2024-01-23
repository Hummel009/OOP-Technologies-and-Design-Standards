package hummel.special

import hummel.transport.CarBasic
import hummel.transport.CarLada
import hummel.transport.CarVolkswagen

fun interface Factory {
	fun createCar(price: Int, color: String, name: String): CarBasic
}

fun interface Engine {
	fun getNewDesc(): String
}

fun interface Listener {
	fun onSpeedChange(car: CarBasic, newSpeed: Int)
}

fun interface Transport {
	fun getTheInfo(): String
}

interface Improvable {
	fun getImprovement(): String
	fun setImprovement(improvement: String): Improvable
}

interface Visitor {
	fun visit(sedan: CarLada)
	fun visit(suv: CarVolkswagen)
}

abstract class Component(val name: String) {
	abstract fun display()
}