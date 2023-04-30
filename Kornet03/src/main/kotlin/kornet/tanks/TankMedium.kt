package kornet.tanks

import kornet.optional.Standard

open class TankMedium(
    name: String = "Panzerkampfwagen III",
    nation: String = "Deutschland",
    level: String = "3",
    member: String = "3",
    private var turningSpeed: String = "30",
    private var margin: String = "5"
) : Tank(name, nation, level, member), Standard {

    override fun printInfo() {
        super.printInfo();
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