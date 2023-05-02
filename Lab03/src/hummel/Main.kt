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
	val shop = Shop
	shop.init()
	val scan = Scanner(System.`in`)
	var type: String

	loop@ while (true) {
		println("Select the type of data saving and loading:")
		type = scan.nextLine()

		when (type) {
			"xml" -> {
				XmlUtils.deserialize(shop)
				break@loop
			}

			"bin" -> {
				StandardUtils.deserialize(shop)
				break@loop
			}

			"json" -> {
				JsonUtils.deserialize(shop)
				break@loop
			}
		}
	}

	loop@ while (true) {
		println("Enter the function:")

		val command = scan.nextLine()
		shop.functions[command]?.invoke()
		if (command == "exit") {
			break@loop
		}
		when (type) {
			"xml" -> {
				XmlUtils.serialize(shop)
			}

			"bin" -> {
				StandardUtils.serialize(shop)
			}

			"json" -> {
				JsonUtils.serialize(shop)
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
		functions["show"] = this::show
		functions["edit"] = this::edit
		functions["sell"] = this::sell
		functions["name"] = this::searchByName
		functions["color"] = this::searchByColor
		functions["price"] = this::searchByPrice
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.loadDefaultList()) }
	}

	private fun edit() {
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

	private fun sell() {
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

	private fun show() {
		for (item in transport) {
			println(item.getTheInfo())
		}
	}

	private fun searchByName() {
		println("Enter the name of the transport")
		val scan = Scanner(System.`in`)
		val str = scan.nextLine()
		var found = false

		for (item in transport) {
			if (item.getTheName() == str) {
				println(item.getTheInfo())
				found = true
			}
		}

		if (!found) {
			println("No info found")
		}
	}

	private fun searchByColor() {
		println("Enter the color of the transport")
		val scan = Scanner(System.`in`)
		val str = scan.nextLine()
		var found = false

		for (item in transport) {
			if (item.getTheColor() == str) {
				println(item.getTheInfo())
				found = true
			}
		}

		if (!found) {
			println("No info found")
		}
	}

	private fun searchByPrice() {
		println("Enter the price of the transport")
		val scan = Scanner(System.`in`)
		val price = scan.nextInt()
		var found = false

		for (item in transport) {
			if (item.getThePrice() == price) {
				println(item.getTheInfo())
				found = true
			}
		}

		if (!found) {
			println("No info found")
		}
	}
}