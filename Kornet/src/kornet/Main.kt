package kornet

import kornet.tanks.*
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