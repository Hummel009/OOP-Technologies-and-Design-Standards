package hummel

import hummel.optional.Editable
import hummel.optional.Improvable
import hummel.transport.*
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import hummel.utils.XmlUtils
import java.util.*
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

fun main() {
	Shop.init()
	val scan = Scanner(System.`in`)
	var type: String

	loop@ while (true) {
		println("Select the type of data saving and loading:")
		type = scan.nextLine()

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
		println("Enter the function:")

		val command = scan.nextLine()
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
}

@XmlRootElement
object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()

	@XmlElementWrapper(name = "transports", nillable = true)
	@JvmStatic
	var transport: MutableList<Transport> = ArrayList()

	fun init() {
		functions["commands"] = this::showAllFunctions
		functions["show"] = this::showAllItems
		functions["sell"] = this::addItem
		functions["edit"] = this::editItem
		functions["search"] = this::searchForItem
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.loadDefaultList()) }
	}
	
	private fun showAllFunctions() {
		for (item in functions.keys) {
			println(item)
		}
	}

	private fun showAllItems() {
		for (item in transport) {
			println(item.getTheInfo())
		}
	}

	private fun editItem() {
		val arr = transport.toTypedArray()
		for (i in arr.indices) {
			println("$i. ${arr[i].getTheInfo()}")
		}
		println("Enter the number of the transport to edit:")
		val scan = Scanner(System.`in`)
		val num = scan.nextLine().toInt()
		if (num > arr.size || num < 0) {
			println("Error")
		} else {
			val transport = arr[num]
			if (transport is Editable) {
				println("Enter the new price")
				val price = scan.nextLine().toInt()
				println("Enter the new color")
				val color = scan.nextLine()
				transport.setThePrice(price)
				transport.setTheColor(color)
			}
			if (transport is Improvable) {
				println("Enter the new improvement")
				val improvement = scan.nextLine()
				transport.setTheImprovement(improvement)
			}
		}
	}

	private fun addItem() {
		println("Enter the name of the transport")
		val scan = Scanner(System.`in`)
		val name = scan.nextLine()

		var className: Class<*>? = null

		try {
			className = Class.forName("hummel.transport.$name")
		} catch (e: Exception) {
			println("Transport wasn't found")
		}

		if (className != null) {
			val obj = className.newInstance() as Transport
			if (obj is Editable) {
				println("Enter the price of the transport")
				val price = scan.nextLine().toInt()
				println("Enter the color of the transport")
				val color = scan.nextLine()
				obj.setTheColor(color)
				obj.setThePrice(price)
			}
			if (obj is Improvable) {
				println("Enter the improvement of the transport")
				val improvement = scan.nextLine()
				obj.setTheImprovement(improvement)
			}

			transport.add(obj)
		}
	}

	private fun searchForItem() {
		println("Enter the type of the search: name, price, color")
		val scan = Scanner(System.`in`)
		val str = scan.nextLine()
		var found = false
		when (str) {
			"name" -> {
				println("Enter the name of the transport")
				val comparing = scan.nextLine()
				transport.forEach { item ->
					if (item.getTheName() == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport")
				val comparing = scan.nextLine().toInt()
				transport.forEach { item ->
					if (item.getThePrice() == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the name of the transport")
				val comparing = scan.nextLine()
				transport.forEach { item ->
					if (item.getTheColor() == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}
		}

		if (!found) {
			println("No info found")
		}
	}
}