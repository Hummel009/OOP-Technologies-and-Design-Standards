package kornet.tanks

class SuperHeavyTank(
    name: String,
    nation: String,
    crewmember: Int,
    level: Int,
    damage: Int,
    time: Double,
    private var mass: Double,
    private var armor: Int
) : HeavyTank(name, nation, crewmember, level, damage, time) {

    override fun printInfo() {
        super.printInfo()
        println("Масса: $mass т.\nБроня во лбу: $armor мм.")
    }
}