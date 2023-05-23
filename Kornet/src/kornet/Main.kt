package kornet

import kornet.tanks.AmogusAdapter
import kornet.tanks.Tank
import kornet.tanks.TankAmogus
import kornet.tanks.TankHeavy
import kornet.utils.JsonUtils
import kornet.utils.StandardUtils
import java.util.*

fun main() {
	val scan = Scanner(System.`in`)
	WorldOfTanks.reloadFunctions()

	loop@ while (true) {
		println("Enter the function:")
		val command = scan.nextLine()

		WorldOfTanks.functions[command]?.invoke()

		if (command == "exit") {
			break@loop
		}
	}
}


object TankGarage {
	private val availableTanks = mutableListOf<TankHeavy>()
	private val allTanks = mutableListOf<TankHeavy>()

	init {
		repeat(3) {
			val car = createNewCar()
			availableTanks.add(car)
			allTanks.add(car)
		}
	}

	fun acquire(): TankHeavy {
		val tank = availableTanks.firstOrNull()
		return if (tank != null) {
			availableTanks.remove(tank)
			tank
		} else {
			val newCar = createNewCar()
			allTanks.add(newCar)
			newCar
		}
	}

	fun release(car: TankHeavy) {
		if (allTanks.contains(car)) {
			availableTanks.add(car)
		}
	}

	private fun createNewCar(): TankHeavy {
		return TankHeavy()
	}
}

object WorldOfTanks {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	var tanks: MutableList<Tank> = ArrayList()
	var plugin = ""

	fun reloadFunctions() {
		functions.clear()
		functions["commands"] = this::showAllFunctions
		functions["show"] = this::showAllTanks
		functions["add"] = this::addTank
		functions["edit"] = this::editTank
		functions["clear"] = { tanks.clear() }
		functions["load"] = { tanks.addAll(StandardUtils.loadDefaultList()) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }
		functions["plugin"] = this::loadPlugin
		functions["convertJsonXml"] = StandardUtils::convertJsonToXml
		functions["convertXmlJson"] = StandardUtils::convertXmlToJson
		functions["test"] = this::testPatterns

		if (plugin != "") {
			val className = StandardUtils.reflectAccess("$plugin.Loader", "$plugin.Loader")

			if (className != null) {
				try {
					val loadMethod = className.getDeclaredMethod("load")
					val loaderInstance = className.newInstance()
					loadMethod.invoke(loaderInstance)
				} catch (e: Exception) {
					println("This plugin has no new functions")
				}
			}
		}
	}

	private fun testPatterns() {
		val tankGarage = TankGarage

		val tank1 = tankGarage.acquire()
		val tank2 = tankGarage.acquire()
		val tank3 = tankGarage.acquire()

		println(tank1)
		println(tank2)
		println(tank3)
		tankGarage.release(tank3)
		tankGarage.release(tank2)
		tankGarage.release(tank1)
		val tank5 = tankGarage.acquire()
		val tank6 = tankGarage.acquire()
		val tank7 = tankGarage.acquire()
		println(tank5)
		println(tank6)
		println(tank7)
		tanks.clear()
		tanks.add(AmogusAdapter(TankAmogus()));
		showAllTanks()
	}

	private fun loadPlugin() {
		println("Enter the name of the plugin (example: plugin_code):")
		val scan = Scanner(System.`in`)
		plugin = scan.nextLine()
		reloadFunctions()
	}

	private fun showAllFunctions() {
		for (item in functions.keys) {
			println(item)
		}
	}

	private fun showAllTanks() {
		for (item in tanks) {
			item.printInfo()
			println()
		}
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

	private fun addTank() {
		println("Enter the name of the class (example: TankLight):")
		val scan = Scanner(System.`in`)
		val name = scan.nextLine()
		val className = StandardUtils.reflectAccess("kornet.tanks.$name", "$plugin.$name")

		className?.let {
			tanks.add(it.newInstance() as Tank)
		} ?: run {
			println("The tank does not exist!")
		}
	}
}