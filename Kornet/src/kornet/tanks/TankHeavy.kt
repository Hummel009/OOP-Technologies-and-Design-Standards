package kornet.tanks

import kornet.optional.Standard

open class TankHeavy(
	name: String = "Panzerkampfwagen V",
	nation: String = "Deutschland",
	level: String = "5",
	member: String = "4",
	private var damage: String = "300",
	private var time: String = "10"
) : Tank(name, nation, level, member), Standard {

	override fun printInfo() {
		super.printInfo()
		println("Огневая мощь(бп): $damage ед.\nВремя перезарядки: $time с.")
	}

	override fun getFirstStandardValue(): String = damage

	override fun setFirstStandardValue(value: String) {
		damage = value
	}

	override fun getSecondStandardValue(): String = time

	override fun setSecondStandardValue(value: String) {
		time = value
	}
}