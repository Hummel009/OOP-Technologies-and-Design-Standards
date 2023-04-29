package kornet.tanks

open class FirstnationTank(
    name: String,
    nation: String,
    private var level: Int,
    private var crewmember: Int
) : Tank(name, nation) {

    private val weapons = HashMap<String, Int>()

    fun addWeapon(name: String, count: Int) {
        weapons[name] = count
    }

    override fun printInfo() {
        super.printInfo()
        println("Уровень: $level")
        println("Кол-во членов экипажа: $crewmember")

        for (item in weapons.entries) {
            println("Название воружения: ${item.value} мм ${item.key}")
        }

        println("Количество доступных орудий: ${weapons.size}")
    }


}