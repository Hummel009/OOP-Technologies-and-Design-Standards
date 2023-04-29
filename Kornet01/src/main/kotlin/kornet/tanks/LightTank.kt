package kornet.tanks

open class LightTank(
    name: String,
    nation: String,
    crewmember: Int,
    level: Int,
    private var enginePower: Int,
    private var radius: Int
) : FirstnationTank(name, nation, crewmember, level) {

    override fun printInfo() {
        super.printInfo()
        println("Мощность двигателя : $enginePower л.с.\nРадиус связи: $radius")
    }
}