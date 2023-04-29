package kornet.tanks

class SuperLightTank(
    name: String,
    nation: String,
    crewmember: Int,
    level: Int,
    enginePower: Int,
    radius: Int,
    private var enlightenedOptics: String,
    private var viewingRange: Int
) : LightTank(name, nation, crewmember, level, enginePower, radius) {

    override fun printInfo() {
        super.printInfo()
        println("Есть ли улучшенная оптика : $enlightenedOptics\nДальность обзора: $viewingRange м.")
    }
}