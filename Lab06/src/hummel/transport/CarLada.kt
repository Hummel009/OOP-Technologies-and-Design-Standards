package hummel.transport

import hummel.special.Visitor

open class CarLada(price: Int = 6000, color: String = "") : CarBasic(price, color, "Lada") {
	override fun accept(visitor: Visitor) = visitor.visit(this)
}