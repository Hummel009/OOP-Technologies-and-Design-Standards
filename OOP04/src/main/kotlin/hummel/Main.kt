package hummel

import hummel.optional.Editable
import hummel.optional.Improvable
import hummel.transport.CarLadaImproved
import hummel.transport.CarVolkswagenImproved
import hummel.transport.Transport
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader
import java.util.*


fun main() {
	val shop = Shop
	val scan = Scanner(System.`in`)
	println("Enter the name of the plugin:")
	shop.plugin = scan.nextLine()
	shop.init()

	JsonUtils.deserialize(shop)

	loop@ while (true) {
		println("Enter the function:")
		val command = scan.nextLine()
		shop.functions[command]?.invoke()
		if (command == "exit") {
			break@loop
		}
		JsonUtils.serialize(shop)
	}
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	var transport: MutableList<Transport> = ArrayList()
	var plugin = ""

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

	private fun show() {
		for (item in transport) {
			println(item.getTheInfo())
		}
	}

	private fun edit() {
		println("Enter the name of the transport:")
		val scan = Scanner(System.`in`)
		val str = scan.nextLine()
		var found = false

		val currentMap = HashMap<Int, Transport>()
		var i = 0
		for (item in transport) {
			if (item.getTheName() == str) {
				currentMap[i++] = item
				println("${i - 1} ${item.getTheInfo()}")
				found = true
			}
		}
		if (!found) {
			println("No info found")
		} else {
			println("Select the transport to edit")
			val num = scan.nextLine().toInt()
			val transport = currentMap[num]
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
			try {
				val pluginFile = File(plugin)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				className = classLoader.loadClass("plugin.$name")
			} catch (e: Exception) {
				println("Transport wasn't found")
			}
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