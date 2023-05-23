package kornet.tanks

import java.io.Serializable

open class Tank(
	var name: String = "T-34",
	var nation: String = "USSR",
	var level: String = "1",
	var member: String = "2"
) : Serializable {
	private val serialVersionUID = 1L

	open fun printInfo() {
		println("Название = $name\nНация = $nation")
		println("Уровень: $level")
		println("Кол-во членов экипажа: $member")
	}
}