package hummel

import hummel.optional.Improvable
import hummel.transport.Transport
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader
import java.nio.charset.StandardCharsets
import java.util.*

fun main() {
	val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
	Shop.reloadFunctions()

	loop@ while (true) {
		println("Enter the command:")
		val command = scanner.nextLine()

		Shop.functions[command]?.invoke()

		if (command == "exit") {
			break@loop
		}
	}

	scanner.close()
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	val transport: MutableList<Transport> = ArrayList()
	var plugin: String = ""

	fun reloadFunctions() {
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
		println("Enter the name of the plugin (example: plugin.jar):")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		plugin = scanner.nextLine()
		scanner.close()
		reloadFunctions()
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
		println("Enter the number of the transport to edit:")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		val index = scanner.nextLine().toInt()
		try {
			val item = arr[index]
			println("Enter the new price:")
			val price = scanner.nextLine().toInt()
			println("Enter the new color:")
			val color = scanner.nextLine()
			item.price = price
			item.color = color
			if (item is Improvable) {
				println("Enter the new improvement:")
				val improvement = scanner.nextLine()
				item.setImprovement(improvement)
			}
		} catch (e: Exception) {
			println("Wrong index!")
		}
		scanner.close()
	}

	private fun addTransport() {
		println("Enter the class name of the transport:")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		val className = scanner.nextLine()
		val clazz = StandardUtils.accessClass("hummel.transport.$className", "plugin.$className")

		if (clazz != null) {
			println("Enter the price of the transport:")
			val price = scanner.nextLine().toInt()
			println("Enter the color of the transport:")
			val color = scanner.nextLine()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				println("Enter the improvement of the transport:")
				val improvement = scanner.nextLine()
				item.setImprovement(improvement)
			}
			transport.add(item)
		}

		scanner.close()
	}

	private fun searchForTransport() {
		println("Enter the type of the search (name, price, color):")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		val type = scanner.nextLine()
		var found = false
		when (type) {
			"name" -> {
				println("Enter the name of the transport:")
				val name = scanner.nextLine()
				for (item in transport) {
					if (item.name == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport:")
				val price = scanner.nextLine().toInt()
				for (item in transport) {
					if (item.price == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the color of the transport:")
				val color = scanner.nextLine()
				for (item in transport) {
					if (item.color == color) {
						println(item.getTheInfo())
						found = true
					}
				}
			}
		}

		scanner.close()

		if (!found) {
			println("Items not found!")
		}
	}
}