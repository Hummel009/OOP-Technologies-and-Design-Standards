package kornet.tanks

class SuperMediumTank(
    name: String,
    nation: String,
    crewmember: Int,
    level: Int,
    turningSpeed: Int,
    margin: Int,
    private var mass: Int,
    private var numb: Int
) : MediumTank(name, nation, crewmember, level, turningSpeed, margin) {

    override fun printInfo() {
        super.printInfo()
        println("Подъёмная масса гусениц : $mass л.с.\nКол-во снарядов: $numb")
    }
}