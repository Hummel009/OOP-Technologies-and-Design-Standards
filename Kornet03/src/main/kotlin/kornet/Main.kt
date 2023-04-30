package kornet

import kornet.tanks.*
import kornet.utils.JsonUtils
import kornet.utils.StandardUtils
import kornet.utils.XmlUtils
import java.util.*
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

fun main() {
	WorldOfTanks.init()
	val scan = Scanner(System.`in`)
	var type: String

	loop@ while (true) {
		println("Select the type of data saving and loading:")
		type = scan.nextLine()

		when (type) {
			"xml" -> {
				XmlUtils.deserialize()
				break@loop
			}

			"bin" -> {
				StandardUtils.deserialize()
				break@loop
			}

			"json" -> {
				JsonUtils.deserialize()
				break@loop
			}
		}
	}

	loop@ while (true) {
		println("Enter the function:")

		val command = scan.nextLine()
		WorldOfTanks.functions[command]?.invoke()
		if (command == "exit") {
			break@loop
		}
		when (type) {
			"xml" -> {
				XmlUtils.serialize()
			}

			"bin" -> {
				StandardUtils.serialize()
			}

			"json" -> {
				JsonUtils.serialize()
			}
		}
	}
}

@XmlRootElement
object WorldOfTanks {
	val functions: MutableMap<String, () -> Unit> = HashMap()

	@XmlElementWrapper(name = "tanks", nillable = true)
	@JvmStatic
	var tanks: MutableList<Tank> = ArrayList()

	fun init() {
		functions["show"] = this::showAllTanks
		functions["edit"] = this::editTank
		functions["add"] = this::addTank
		functions["clear"] = { tanks.clear() }
		functions["load"] = { tanks.addAll(StandardUtils.loadDefaultList()) }
	}

	private fun addTank() {
		val scan = Scanner(System.`in`)
		println("Enter the name: ")

		val tank = when (scan.nextLine()) {
			"TankHeavy" -> TankHeavy()
			"TankLight" -> TankLight()
			"TankMedium" -> TankMedium()
			"TankSuperHeavy" -> TankSuperHeavy()
			"TankSuperLight" -> TankSuperLight()
			"TankSuperMedium" -> TankSuperMedium()
			else -> Tank()
		}
		tanks.add(tank)
	}

	private fun editTank() {
		val array = tanks.toTypedArray()
		var i = 0
		array.forEach { println("${i++}. ${it.name}") }
		val scan = Scanner(System.`in`)
		println("Select the tank")
		val id = scan.nextLine().toInt()
		val tank = array[id]
		println("Select the new name of tank")
		val name = scan.nextLine()
		tank.name = name
	}

	private fun showAllTanks() {
		for (item in tanks) {
			item.printInfo()
			println()
		}
	}
}