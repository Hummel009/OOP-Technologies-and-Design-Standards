package com.github.hummel.ooptds.lab3

import com.github.hummel.ooptds.lab3.optional.Improvable
import com.github.hummel.ooptds.lab3.transport.Transport
import com.github.hummel.ooptds.lab3.utils.JsonUtils
import com.github.hummel.ooptds.lab3.utils.StandardUtils
import com.github.hummel.ooptds.lab3.utils.XmlUtils
import jakarta.xml.bind.annotation.XmlElementWrapper
import jakarta.xml.bind.annotation.XmlRootElement

fun main() {
	val type = generateSequence {
		print("Select the type of data saving and loading: ")
		readln()
	}.first { input ->
		when (input) {
			"xml" -> {
				XmlUtils.deserialize(); true
			}

			"bin" -> {
				StandardUtils.deserialize(); true
			}

			"json" -> {
				JsonUtils.deserialize(); true
			}

			else -> false
		}
	}

	Shop.init()
	loop@ while (true) {
		print("Enter the command: ")
		val command = readln()

		if (command == "exit") {
			break@loop
		}

		Shop.functions[command]?.invoke() ?: println("Unknown command!")

		when (type) {
			"xml" -> XmlUtils.serialize()
			"bin" -> StandardUtils.serialize()
			"json" -> JsonUtils.serialize()
		}
	}
}

@XmlRootElement
object Shop {
	@XmlElementWrapper(name = "transports", nillable = true)
	@JvmStatic
	val transport: MutableList<Transport> = ArrayList()
	val functions: MutableMap<String, () -> Unit> = HashMap()

	fun init() {
		functions["commands"] = ::showAllCommands
		functions["show"] = ::showAllTransport
		functions["sell"] = ::addTransport
		functions["edit"] = ::editTransport
		functions["search"] = ::searchForTransport
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
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
		try {
			val clazz = Class.forName("com.github.hummel.ooptds.lab3.transport.$className")
			print("Enter the price of the transport: ")
			val price = readIntSafe()
			print("Enter the color of the transport: ")
			val color = readln()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				print("Enter the improvement of the transport: ")
				val improvement = readln()
				item.setImprovement(improvement)
			}
			transport.add(item)
		} catch (_: Exception) {
			println("Class not found!")
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
	} catch (_: Exception) {
		print("Error! Enter the correct value: ")
		readIntSafe()
	}
}