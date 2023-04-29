package kornet.tanks

open class HeavyTank(
    name: String,
    nation: String,
    crewmember: Int,
    level: Int,
    private var damage: Int,
    private var time: Double
) : FirstnationTank(name, nation, crewmember, level) {

    override fun printInfo() {
        super.printInfo()
        println("Огневая мощь(бп): $damage ед.\nВремя перезарядки: $time с.")
    }
}