package plugin_code

import kornet.optional.Super
import kornet.tanks.TankHeavy

class TankSuperHeavy2(
	name: String = "RATTE",
	nation: String = "Deutschland",
	level: String = "60",
	member: String = "40",
	damage: String = "3000",
	time: String = "100",
	private var mass: String = "760",
	private var armor: String = "5000"
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