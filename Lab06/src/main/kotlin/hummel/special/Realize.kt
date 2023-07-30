package hummel.special

import hummel.transport.*

class ComponentEngine(name: String) : Component(name) {
	override fun display() {
		println("Engine: $name")
	}
}

class ComponentTransmission(name: String) : Component(name) {
	override fun display() {
		println("Transmission: $name")
	}
}

class ComponentSuspension(name: String) : Component(name) {
	override fun display() {
		println("Suspension: $name")
	}
}

class EngineGas : Engine {
	override fun getNewDesc(): String {
		return "Gas"
	}
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
	override fun visit(sedan: CarLada) {
		println(sedan.getTheInfo() + " It's a great car for fixing.")
	}

	override fun visit(suv: CarVolkswagen) {
		println(suv.getTheInfo() + " It's a great car for adventures.")
	}
}