package kornet.tanks

import kornet.optional.Super

class TankSuperLight(
	name: String = "Panzerkampfwagen I",
	nation: String = "Deutschland",
	level: String = "1",
	member: String = "1",
	enginePower: String = "300",
	radius: String = "20",
	private var enlightenedOptics: String = "50",
	private var viewingRange: String = "40"
) : TankLight(name, nation, level, member, enginePower, radius), Super {

	override fun printInfo() {
		super.printInfo()
		println("Есть ли улучшенная оптика : $enlightenedOptics\nДальность обзора: $viewingRange м.")
	}

	override fun getFirstSuperValue(): String = enlightenedOptics

	override fun setFirstSuperValue(value: String) {
		enlightenedOptics = value
	}

	override fun getSecondSuperValue(): String = viewingRange

	override fun setSecondSuperValue(value: String) {
		viewingRange = value
	}
}