package hummel

import hummel.transport.Transport
import hummel.util.StandardUtils
import java.nio.charset.StandardCharsets
import java.util.*

val scanner: Scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())

fun main() {
	Shop.init()
	loop@ while (true) {
		print("Enter the command: ")
		val command = scanner.nextLine()

		Shop.functions[command]?.invoke() ?: println("Unknown command!")

		if (command == "exit") {
			break@loop
		}
	}
	scanner.close()
}

object Shop {
	private val transport: MutableList<Transport> = ArrayList()
	val functions: MutableMap<String, () -> Unit> = HashMap()

	fun init() {
		functions["commands"] = this::showAllCommands
		functions["show"] = this::showAllTransport
		functions["search"] = this::searchForTransport
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

	private fun searchForTransport() {
		print("Enter the type of the search (name, price, color): ")
		val type = scanner.nextLine()
		var found = false
		when (type) {
			"name" -> {
				print("Enter the name of the transport: ")
				val name = scanner.nextLine()
				for (item in transport) {
					if (item.getTheName() == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				print("Enter the price of the transport: ")
				val price = scanner.nextIntSafe()
				for (item in transport) {
					if (item.getThePrice() == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				print("Enter the color of the transport: ")
				val color = scanner.nextLine()
				for (item in transport) {
					if (item.getTheColor() == color) {
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