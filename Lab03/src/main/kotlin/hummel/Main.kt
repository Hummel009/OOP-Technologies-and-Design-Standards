package hummel

import hummel.optional.Improvable
import hummel.transport.Transport
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import hummel.utils.XmlUtils
import java.nio.charset.StandardCharsets
import java.util.*
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

fun main() {
	Shop.initFunctions()
	val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
	var type: String

	loop@ while (true) {
		println("Select the type of data saving and loading:")
		type = scanner.nextLine()

		when (type) {
			"xml" -> {
				XmlUtils.deserialize()
				break@loop
			}

			"bin" -> {
				StandardUtils.deserialize()
				break@loop
			}

			"json" -> {
				JsonUtils.deserialize()
				break@loop
			}
		}
	}

	loop@ while (true) {
		println("Enter the command:")

		val command = scanner.nextLine()
		Shop.functions[command]?.invoke()
		if (command == "exit") {
			break@loop
		}
		when (type) {
			"xml" -> {
				XmlUtils.serialize()
			}

			"bin" -> {
				StandardUtils.serialize()
			}

			"json" -> {
				JsonUtils.serialize()
			}
		}
	}

	scanner.close()
}

@XmlRootElement
object Shop {
	@XmlElementWrapper(name = "transports", nillable = true)
	@JvmStatic
	val transport: MutableList<Transport> = ArrayList()
	val functions: MutableMap<String, () -> Unit> = HashMap()

	fun initFunctions() {
		functions["commands"] = this::showAllCommands
		functions["show"] = this::showAllTransport
		functions["sell"] = this::addTransport
		functions["edit"] = this::editTransport
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

	private fun editTransport() {
		val arr = transport.toTypedArray()
		for (i in arr.indices) {
			println("$i. ${arr[i].getTheInfo()}")
		}
		println("Enter the number of the transport to edit:")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		val index = scanner.nextLine().toInt()
		try {
			val item = arr[index]
			println("Enter the new price:")
			val price = scanner.nextLine().toInt()
			println("Enter the new color:")
			val color = scanner.nextLine()
			item.price = price
			item.color = color
			if (item is Improvable) {
				println("Enter the new improvement:")
				val improvement = scanner.nextLine()
				item.setImprovement(improvement)
			}
		} catch (e: Exception) {
			println("Wrong index!")
		}

		scanner.close()
	}

	private fun addTransport() {
		println("Enter the class name of the transport:")
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		val className = scanner.nextLine()

		try {
			val clazz = Class.forName("hummel.transport.$className")
			println("Enter the price of the transport:")
			val price = scanner.nextLine().toInt()
			println("Enter the color of the transport:")
			val color = scanner.nextLine()
			val item = clazz.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				println("Enter the improvement of the transport:")
				val improvement = scanner.nextLine()
				item.setImprovement(improvement)
			}

			transport.add(item)
		} catch (e: Exception) {
			println("Class not found!")
		}

		scanner.close()
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
					if (item.name == name) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport:")
				val price = scanner.nextLine().toInt()
				for (item in transport) {
					if (item.price == price) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the color of the transport:")
				val color = scanner.nextLine()
				for (item in transport) {
					if (item.color == color) {
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