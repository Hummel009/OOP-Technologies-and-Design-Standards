package hummel

import hummel.optional.Improvable
import hummel.transport.Transport
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.util.*

fun main() {
	val scan = Scanner(System.`in`)
	Shop.init()

	loop@ while (true) {
		println("Enter the function:")
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
	var plugin = ""

	fun init() {
		functions.clear()
		functions["commands"] = this::showAllCommands
		functions["show"] = this::showAllItems
		functions["sell"] = this::addItem
		functions["edit"] = this::editItem
		functions["search"] = this::search
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.loadDefaultList()) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }
		functions["plugin"] = this::loadPlugin
		functions["convertJsonXml"] = StandardUtils::convertJsonToXml
		functions["convertXmlJson"] = StandardUtils::convertXmlToJson

		if (plugin != "") {
			val className = StandardUtils.reflectAccess("plugin.Loader", "plugin.Loader")

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
		println("Enter the name of the plugin:")
		val scan = Scanner(System.`in`)
		plugin = scan.nextLine()
		init()
	}

	private fun showAllCommands() {
		functions.keys.forEach { println(it) }
	}

	private fun showAllItems() {
		transport.forEach { println(it.getTheInfo()) }
	}

	private fun editItem() {
		val arr = transport.toTypedArray()
		for (i in arr.indices) {
			println("$i. ${arr[i].getTheInfo()}")
		}
		println("Enter the number of the transport to edit:")
		val scan = Scanner(System.`in`)
		val num = scan.nextLine().toInt()
		if (num > arr.size || num < 0) {
			println("Error")
		} else {
			val transport = arr[num]
			println("Enter the new price")
			val price = scan.nextLine().toInt()
			println("Enter the new color")
			val color = scan.nextLine()
			transport.price = price
			transport.color = color
			if (transport is Improvable) {
				println("Enter the new improvement")
				val improvement = scan.nextLine()
				transport.setImprovement(improvement)
			}
		}
	}

	private fun addItem() {
		println("Enter the name of the transport")
		val scan = Scanner(System.`in`)
		val name = scan.nextLine()
		val className = StandardUtils.reflectAccess("hummel.transport.$name", "plugin.$name")

		if (className != null) {
			println("Enter the price of the transport")
			val price = scan.nextLine().toInt()
			println("Enter the color of the transport")
			val color = scan.nextLine()
			val obj =
				className.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (obj is Improvable) {
				println("Enter the improvement of the transport")
				val improvement = scan.nextLine()
				obj.setImprovement(improvement)
			}
			transport.add(obj)
		}
	}

	private fun search() {
		println("Enter the type of the search: name, price, color")
		val scan = Scanner(System.`in`)
		val str = scan.nextLine()
		var found = false
		when (str) {
			"name" -> {
				println("Enter the name of the transport")
				val comparing = scan.nextLine()
				transport.forEach { item ->
					if (item.name == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport")
				val comparing = scan.nextLine().toInt()
				transport.forEach { item ->
					if (item.price == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the name of the transport")
				val comparing = scan.nextLine()
				transport.forEach { item ->
					if (item.color == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}
		}

		if (!found) {
			println("No info found")
		}
	}
}