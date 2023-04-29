package hummel.transport

import hummel.special.Visitor

open class CarVolkswagen(price: Int = 18000, color: String = "") : CarBasic(price, color, "Volkswagen") {
	override fun accept(visitor: Visitor) = visitor.visit(this)
}