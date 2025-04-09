package com.github.hummel.ooptds.lab2

import com.github.hummel.ooptds.lab2.transport.Transport
import com.github.hummel.ooptds.lab2.util.StandardUtils

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
	private val transport: MutableList<Transport> = ArrayList()
	val functions: MutableMap<String, () -> Unit> = HashMap()

	fun init() {
		functions["commands"] = ::showAllCommands
		functions["show"] = ::showAllTransport
		functions["search"] = ::searchForTransport
		functions["sell"] = ::addTransport
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
	}

	private fun showAllCommands() {
		functions.keys.forEach { println(it) }
	}

	private fun showAllTransport() {
		transport.forEach { println(it.getTheInfo()) }
	}

	private fun addTransport() {
		print("Enter the class name of the transport: ")
		val className = readln()
		try {
			val clazz = Class.forName("com.github.hummel.ooptds.lab2.transport.$className")
			print("Enter the price of the transport: ")
			val price = readIntSafe()
			print("Enter the color of the transport: ")
			val color = readln()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
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