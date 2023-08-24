package hummel.special

import hummel.transport.*

class ComponentEngine(name: String) : Component(name) {
	override fun display(): Unit = println("Engine: $name")
}

class ComponentTransmission(name: String) : Component(name) {
	override fun display(): Unit = println("Transmission: $name")
}

class ComponentSuspension(name: String) : Component(name) {
	override fun display(): Unit = println("Suspension: $name")
}

class EngineGas : Engine {
	override fun getNewDesc(): String = "Gas"
}

class FunctionalFactory : Factory {
	override fun createCar(price: Int, color: String, name: String): CarBasic {
		return when (name) {
			"CarLada" -> CarLada(price, color)
			"CarPeugeot" -> Adapter(CarPeugeot())
			"CarVolkswagen" -> CarVolkswagen(price, color)
			else -> throw Exception()
		}
	}
}


class FunctionalVisitor : Visitor {
	override fun visit(sedan: CarLada): Unit = println(sedan.getTheInfo() + " Car for fixing.")

	override fun visit(suv: CarVolkswagen): Unit = println(suv.getTheInfo() + " Car for adventures.")
}