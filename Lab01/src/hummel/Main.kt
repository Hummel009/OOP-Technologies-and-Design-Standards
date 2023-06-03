package hummel

import hummel.transport.*
import hummel.util.StandardUtils
import java.util.*

fun main() {
	Shop.initFunctions()
	val scan = Scanner(System.`in`)

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
		val scan = Scanner(System.`in`)
		val type = scan.nextLine()
		var found = false
		when (type) {
			"name" -> {
				println("Enter the name of the transport:")
				val name = scan.nextLine()
				for (item in transport) {
					if (item.getTheName() == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport:")
				val price = scan.nextLine().toInt()
				for (item in transport) {
					if (item.getThePrice() == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the color of the transport:")
				val color = scan.nextLine()
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