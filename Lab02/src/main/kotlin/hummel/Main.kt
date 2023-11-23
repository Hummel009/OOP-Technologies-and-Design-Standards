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
		functions["commands"] = this::showAllCommands
		functions["show"] = this::showAllTransport
		functions["search"] = this::searchForTransport
		functions["sell"] = this::addTransport
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
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

	private fun addTransport() {
		print("Enter the class name of the transport: ")
		val className = readln()
		try {
			val clazz = Class.forName("hummel.transport.$className")
			print("Enter the price of the transport: ")
			val price = readIntSafe()
			print("Enter the color of the transport: ")
			val color = readln()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			transport.add(item)
		} catch (e: Exception) {
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
				for (item in transport) {
					if (item.name == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				print("Enter the price of the transport: ")
				val price = readIntSafe()
				for (item in transport) {
					if (item.price == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				print("Enter the color of the transport: ")
				val color = readln()
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

fun readIntSafe(): Int {
	return try {
		readln().toInt()
	} catch (e: Exception) {
		print("Error! Enter the correct value: ")
		readIntSafe()
	}
}