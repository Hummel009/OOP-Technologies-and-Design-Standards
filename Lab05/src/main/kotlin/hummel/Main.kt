package hummel

import hummel.optional.Improvable
import hummel.transport.Transport
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader
import java.nio.charset.StandardCharsets
import java.util.*

val scanner: Scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())

fun main() {
	Shop.init()
	loop@ while (true) {
		print("Enter the command: ")
		val command = scanner.nextLine()

		if (command == "exit") {
			break@loop
		}

		Shop.functions[command]?.invoke() ?: println("Unknown command!")
	}
	scanner.close()
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	val transport: MutableList<Transport> = ArrayList()
	var plugin: String = ""

	fun init() {
		functions.clear()
		functions["commands"] = this::showAllCommands
		functions["show"] = this::showAllTransport
		functions["sell"] = this::addTransport
		functions["edit"] = this::editTransport
		functions["search"] = this::searchForTransport
		functions["plugin"] = this::loadPlugin
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }
		functions["convertJsonXml"] = StandardUtils::convertJsonToXml
		functions["convertXmlJson"] = StandardUtils::convertXmlToJson

		if (plugin != "") {
			try {
				val pluginFile = File(plugin)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				val clazz = classLoader.loadClass("plugin.Loader")
				val loadMethod = clazz.getDeclaredMethod("load")
				val loaderInstance = clazz.newInstance()
				loadMethod.invoke(loaderInstance)
			} catch (e: Exception) {
				println("This plugin don't exist or has no new functions. That's ok, if plugin contains only objects.")
			}
		}
	}

	private fun loadPlugin() {
		print("Enter the name of the plugin (example: plugin.jar): ")
		plugin = scanner.nextLine()

		init()
	}

	private fun showAllCommands() {
		for (item in functions.keys) {
			println(item)
		}
	}

	private fun showAllTransport() {
		for (item in transport) {
			println(item.getTheInfo())
		}
	}

	private fun editTransport() {
		val arr = transport.toTypedArray()
		for (i in arr.indices) {
			println("$i. ${arr[i].getTheInfo()}")
		}
		print("Enter the number of the transport to edit: ")
		val index = scanner.nextIntSafe()
		try {
			val item = arr[index]
			print("Enter the new price: ")
			val price = scanner.nextIntSafe()
			print("Enter the new color: ")
			val color = scanner.nextLine()
			item.price = price
			item.color = color
			if (item is Improvable) {
				print("Enter the new improvement: ")
				val improvement = scanner.nextLine()
				item.setImprovement(improvement)
			}
		} catch (e: Exception) {
			println("Wrong index!")
		}
	}

	private fun addTransport() {
		print("Enter the class name of the transport: ")
		val className = scanner.nextLine()
		val clazz = StandardUtils.accessClass("hummel.transport.$className", "plugin.$className")
		if (clazz != null) {
			print("Enter the price of the transport: ")
			val price = scanner.nextIntSafe()
			print("Enter the color of the transport: ")
			val color = scanner.nextLine()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				print("Enter the improvement of the transport: ")
				val improvement = scanner.nextLine()
				item.setImprovement(improvement)
			}
			transport.add(item)
		}
	}

	private fun searchForTransport() {
		print("Enter the type of the search (name, price, color): ")
		val type = scanner.nextLine()
		var found = false
		when (type) {
			"name" -> {
				print("Enter the name of the transport: ")
				val name = scanner.nextLine()
				for (item in transport) {
					if (item.name == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				print("Enter the price of the transport: ")
				val price = scanner.nextIntSafe()
				for (item in transport) {
					if (item.price == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				print("Enter the color of the transport: ")
				val color = scanner.nextLine()
				for (item in transport) {
					if (item.color == color) {
						println(item.getTheInfo())
						found = true
					}
				}
			}
		}

		if (!found) {
			println("Items not found!")
		}
	}
}

fun Scanner.nextIntSafe(): Int {
	return try {
		val str = nextLine()
		val num = str.toInt()
		num
	} catch (e: Exception) {
		print("Error! Enter the correct value: ")
		nextIntSafe()
	}
}