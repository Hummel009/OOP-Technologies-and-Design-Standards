package hummel

import hummel.optional.Improvable
import hummel.transport.Transport
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader
import java.util.*

fun main() {
	val scan = Scanner(System.`in`)
	Shop.reloadFunctions()

	loop@ while (true) {
		println("Enter the command:")
		val command = scan.nextLine()

		Shop.functions[command]?.invoke()

		if (command == "exit") {
			break@loop
		}
	}
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	var transport: MutableList<Transport> = ArrayList()
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
		functions["load"] = { transport.addAll(StandardUtils.loadDefaultList()) }
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
		val scan = Scanner(System.`in`)
		plugin = scan.nextLine()
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
		val scan = Scanner(System.`in`)
		val index = scan.nextLine().toInt()
		try {
			val item = arr[index]
			println("Enter the new price:")
			val price = scan.nextLine().toInt()
			println("Enter the new color:")
			val color = scan.nextLine()
			item.price = price
			item.color = color
			if (item is Improvable) {
				println("Enter the new improvement:")
				val improvement = scan.nextLine()
				item.setImprovement(improvement)
			}
		} catch (e: Exception) {
			println("Wrong index!")
		}
	}

	private fun addTransport() {
		println("Enter the class name of the transport:")
		val scan = Scanner(System.`in`)
		val className = scan.nextLine()
		val clazz = StandardUtils.accessClass("hummel.transport.$className", "plugin.$className")

		if (clazz != null) {
			println("Enter the price of the transport:")
			val price = scan.nextLine().toInt()
			println("Enter the color of the transport:")
			val color = scan.nextLine()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				println("Enter the improvement of the transport:")
				val improvement = scan.nextLine()
				item.setImprovement(improvement)
			}
			transport.add(item)
		}
	}

	private fun searchForTransport() {
		println("Enter the type of the search (name, price, color):")
		val scan = Scanner(System.`in`)
		val type = scan.nextLine()
		var found = false
		when (type) {
			"name" -> {
				println("Enter the name of the transport:")
				val name = scan.nextLine()
				for (item in transport) {
					if (item.name == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport:")
				val price = scan.nextLine().toInt()
				for (item in transport) {
					if (item.price == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the color of the transport:")
				val color = scan.nextLine()
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