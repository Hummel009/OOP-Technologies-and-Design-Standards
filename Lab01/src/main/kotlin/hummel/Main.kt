package hummel

import hummel.transport.Transport
import hummel.util.StandardUtils
import java.nio.charset.StandardCharsets
import java.util.*

fun main() {
	Shop.initFunctions()
	val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())

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
	private val transport: MutableList<Transport> = ArrayList()
	val functions: MutableMap<String, () -> Unit> = HashMap()

	fun initFunctions() {
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
		println("Enter the type of the search (name, price, color):")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		val type = scanner.nextLine()
		var found = false
		when (type) {
			"name" -> {
				println("Enter the name of the transport:")
				val name = scanner.nextLine()
				for (item in transport) {
					if (item.getTheName() == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport:")
				val price = scanner.nextLine().toInt()
				for (item in transport) {
					if (item.getThePrice() == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the color of the transport:")
				val color = scanner.nextLine()
				for (item in transport) {
					if (item.getTheColor() == color) {
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