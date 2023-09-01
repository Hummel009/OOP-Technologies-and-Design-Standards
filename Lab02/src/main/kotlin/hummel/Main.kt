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

		if (command == "exit") {
			break@loop
		}

		Shop.functions[command]?.invoke() ?: println("Unknown command!")
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
		val className = scanner.nextLine()
		try {
			val clazz = Class.forName("hummel.transport.$className")
			print("Enter the price of the transport: ")
			val price = scanner.nextIntSafe()
			print("Enter the color of the transport: ")
			val color = scanner.nextLine()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			transport.add(item)
		} catch (e: Exception) {
			println("Class not found!")
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