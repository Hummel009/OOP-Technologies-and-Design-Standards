package hummel.transport

import hummel.special.Transport

class Decorator(private val car: Transport) : Transport {
	override fun getTheInfo(): String = car.getTheInfo() + " NAVIGATION INCLUDED "
}