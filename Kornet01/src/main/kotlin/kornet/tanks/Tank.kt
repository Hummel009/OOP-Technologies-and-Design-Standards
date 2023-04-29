package kornet.tanks

open class Tank(
    private var name: String,
    private var nation: String
) {
    open fun printInfo() {
        println("Название = $name\nНация = $nation")
    }
}