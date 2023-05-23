package kornet.tanks

import kornet.optional.Standard

open class TankAmogus(
	public var nation: String = "Deutschland",
	public var name: String = "Panzerkampfwagen III",
	public var member: String = "3",
	public var level: String = "3",
	public var margin: String = "5",
	public var turningSpeed: String = "30"
) : Standard {

	fun printSomething() {
		println("Скорость поворота шасси: $turningSpeed град/с\nЗапас прочности: $margin ед.")
	}

	override fun getFirstStandardValue(): String = turningSpeed

	override fun setFirstStandardValue(value: String) {
		turningSpeed = value
	}

	override fun getSecondStandardValue(): String = margin

	override fun setSecondStandardValue(value: String) {
		margin = value
	}
}