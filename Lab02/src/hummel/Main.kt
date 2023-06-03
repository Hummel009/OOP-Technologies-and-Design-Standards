package hummel

import hummel.transport.Transport
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
		functions["sell"] = this::addTransport
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.loadDefaultList()) }
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
		println("Enter the class name of the transport:")
		val scan = Scanner(System.`in`)
		val className = scan.nextLine()

		try {
			val clazz = Class.forName("hummel.transport.$className")
			println("Enter the price of the transport:")
			val price = scan.nextLine().toInt()
			println("Enter the color of the transport:")
			val color = scan.nextLine()

			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport

			transport.add(item)
		} catch (e: Exception) {
			println("Class not found!")
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