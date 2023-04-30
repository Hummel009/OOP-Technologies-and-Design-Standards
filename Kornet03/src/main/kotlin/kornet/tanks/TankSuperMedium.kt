package kornet.tanks

import kornet.optional.Super

class TankSuperMedium(
    name: String = "Panzerkampfwagen IV",
    nation: String = "Deutschland",
    level: String = "4",
    member: String = "3",
    turningSpeed: String = "30",
    margin: String = "5",
    private var mass: String = "46",
    private var numb: String = "99"
) : TankMedium(name, nation, level, member, turningSpeed, margin), Super {

    override fun printInfo() {
        super.printInfo()
        println("Подъёмная масса гусениц : $mass л.с.\nКол-во снарядов: $numb")
    }

    override fun getFirstSuperValue(): String = mass

    override fun setFirstSuperValue(value: String) {
        mass = value
    }

    override fun getSecondSuperValue(): String = numb

    override fun setSecondSuperValue(value: String) {
        numb = value
    }
}