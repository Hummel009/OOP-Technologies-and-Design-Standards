package kornet.tanks

import kornet.optional.Super

class TankSuperHeavy(
	name: String = "Panzerkampfwagen VI",
	nation: String = "Deutschland",
	level: String = "6",
	member: String = "4",
	damage: String = "300",
	time: String = "10",
	private var mass: String = "76",
	private var armor: String = "500"
) : TankHeavy(name, nation, level, member, damage, time), Super {

	override fun printInfo() {
		super.printInfo()
		println("Масса: $mass т.\nБроня во лбу: $armor мм.")
	}

	override fun getFirstSuperValue(): String = mass

	override fun setFirstSuperValue(value: String) {
		mass = value
	}

	override fun getSecondSuperValue(): String = armor

	override fun setSecondSuperValue(value: String) {
		armor = value
	}
}