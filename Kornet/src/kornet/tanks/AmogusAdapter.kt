package kornet.tanks

import kornet.optional.Standard

open class AmogusAdapter(
	private var tank: TankAmogus
) : Tank(tank.name, tank.nation, tank.level, tank.member), Standard {

	override fun printInfo() {
		tank.printSomething()
	}

	override fun getFirstStandardValue(): String = tank.turningSpeed

	override fun setFirstStandardValue(value: String) {
		tank.turningSpeed = value
	}

	override fun getSecondStandardValue(): String = tank.margin

	override fun setSecondStandardValue(value: String) {
		tank.margin = value
	}
}