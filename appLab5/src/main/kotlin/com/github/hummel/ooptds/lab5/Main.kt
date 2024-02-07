package com.github.hummel.ooptds.lab5

import com.github.hummel.ooptds.lab5.optional.Improvable
import com.github.hummel.ooptds.lab5.transport.Transport
import com.github.hummel.ooptds.lab5.utils.JsonUtils
import com.github.hummel.ooptds.lab5.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader

fun main() {
	Shop.init()
	loop@ while (true) {
		print("Enter the command: ")
		val command = readln()

		if (command == "exit") {
			break@loop
		}

		Shop.functions[command]?.invoke() ?: println("Unknown command!")
	}
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	val transport: MutableList<Transport> = ArrayList()
	var plugin: String? = null

	fun init() {
		functions.clear()
		functions["commands"] = ::showAllCommands
		functions["show"] = ::showAllTransport
		functions["sell"] = ::addTransport
		functions["edit"] = ::editTransport
		functions["search"] = ::searchForTransport
		functions["plugin"] = ::loadPlugin
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }
		functions["convertJsonXml"] = StandardUtils::convertJsonToXml
		functions["convertXmlJson"] = StandardUtils::convertXmlToJson

		plugin?.let {
			try {
				val pluginFile = File(it)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				val clazz = classLoader.loadClass("plugin.Loader")
				val loadMethod = clazz.getDeclaredMethod("load")
				val loaderInstance = clazz.getConstructor().newInstance()
				loadMethod.invoke(loaderInstance)
			} catch (e: Exception) {
				println("This plugin don't exist or has no new functions. That's ok, if plugin contains only objects.")
			}
		}
	}

	private fun loadPlugin() {
		print("Enter the name of the plugin (example: plugin.jar): ")
		plugin = readln()
		init()
	}

	private fun showAllCommands() {
		functions.keys.forEach { println(it) }
	}

	private fun showAllTransport() {
		transport.forEach { println(it.getTheInfo()) }
	}

	private fun editTransport() {
		val arr = transport.toTypedArray()
		arr.forEachIndexed { i, item -> println("$i. ${item.getTheInfo()}") }
		print("Enter the number of the transport to edit: ")
		val index = readIntSafe()
		if (index in arr.indices) {
			val item = arr[index]
			print("Enter the new price: ")
			val price = readIntSafe()
			print("Enter the new color: ")
			val color = readln()
			item.price = price
			item.color = color
			if (item is Improvable) {
				print("Enter the new improvement: ")
				val improvement = readln()
				item.setImprovement(improvement)
			}
		} else {
			println("Wrong index!")
		}
	}

	private fun addTransport() {
		print("Enter the class name of the transport: ")
		val className = readln()
		val clazz = StandardUtils.accessClass("com.github.hummel.ooptds.lab5.transport.$className", "plugin.$className")
		clazz?.let {
			print("Enter the price of the transport: ")
			val price = readIntSafe()
			print("Enter the color of the transport: ")
			val color = readln()
			val item = it.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				print("Enter the improvement of the transport: ")
				val improvement = readln()
				item.setImprovement(improvement)
			}
			transport.add(item)
		}
	}

	private fun searchForTransport() {
		print("Enter the type of the search (name, price, color): ")
		val type = readln()
		var found = false
		when (type) {
			"name" -> {
				print("Enter the name of the transport: ")
				val name = readln()
				transport.asSequence().filter { it.name == name }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}

			"price" -> {
				print("Enter the price of the transport: ")
				val price = readIntSafe()
				transport.asSequence().filter { it.price == price }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}

			"color" -> {
				print("Enter the color of the transport: ")
				val color = readln()
				transport.asSequence().filter { it.color == color }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}
		}

		if (!found) {
			println("Items not found!")
		}
	}
}

fun readIntSafe(): Int {
	return try {
		readln().toInt()
	} catch (e: Exception) {
		print("Error! Enter the correct value: ")
		readIntSafe()
	}
}