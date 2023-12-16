package hummel

import hummel.transport.Transport
import hummel.util.StandardUtils

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
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
	}

	private fun showAllCommands() {
		functions.keys.forEach { println(it) }
	}

	private fun showAllTransport() {
		transport.forEach { println(it.getTheInfo()) }
	}

	private fun searchForTransport() {
		print("Enter the type of the search (name, price, color): ")
		val type = readln()
		var found = false
		when (type) {
			"name" -> {
				print("Enter the name of the transport: ")
				val name = readln()
				transport.asSequence().filter { it.getTheName() == name }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}

			"price" -> {
				print("Enter the price of the transport: ")
				val price = readIntSafe()
				transport.asSequence().filter { it.getThePrice() == price }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}

			"color" -> {
				print("Enter the color of the transport: ")
				val color = readln()
				transport.asSequence().filter { it.getTheColor() == color }.forEach {
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