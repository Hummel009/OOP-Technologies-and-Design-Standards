package kornet.tanks

import kornet.optional.Standard

open class TankLight(
    name: String = "Panzerkampfwagen II",
    nation: String = "Deutschland",
    level: String = "2",
    member: String = "2",
    private var enginePower: String = "300",
    private var radius: String = "20"
) : Tank(name, nation, level, member), Standard {

    override fun printInfo() {
        super.printInfo()
        println("Мощность двигателя : $enginePower л.с.\nРадиус связи: $radius")
    }

    override fun getFirstStandardValue(): String = enginePower

    override fun setFirstStandardValue(value: String) {
        enginePower = value
    }

    override fun getSecondStandardValue(): String = radius

    override fun setSecondStandardValue(value: String) {
        radius = value
    }
}