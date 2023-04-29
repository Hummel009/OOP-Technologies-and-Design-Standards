package kornet.tanks

open class MediumTank(
    name: String,
    nation: String,
    crewmember: Int,
    level: Int,
    private var turningSpeed: Int,
    private var margin: Int
) : FirstnationTank(name, nation, crewmember, level) {

    override fun printInfo() {
        super.printInfo();
        println("МСкорость поворота шасси: $turningSpeed град/с\nЗапас прочности: $margin ед.")
    }
}