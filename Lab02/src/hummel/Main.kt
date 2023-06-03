package hummel

import java.util.*

val transport: MutableList<Transport> = ArrayList()
val functions: MutableMap<String, () -> Unit> = HashMap()

fun main() {
	init()
	val scan = Scanner(System.`in`)

	loop@ while (true) {
		println("Enter the command:")

		val command = scan.nextLine()
		functions[command]?.invoke()

		if (command == "exit") {
			break@loop
		}
	}
}

fun init() {
	functions["commands"] = ::showAllCommands
	functions["show"] = ::showAllTransport
	functions["color"] = ::searchByColor
	functions["price"] = ::searchByPrice
	functions["name"] = ::searchByName
	functions["sell"] = ::addTransport
	functions["clear"] = { transport.clear() }
	functions["load"] = { transport.addAll(loadDefaultList()) }
}

fun showAllCommands() {
	for (item in functions.keys) {
		println(item)
	}
}

fun showAllTransport() {
	for (item in transport) {
		println(item.getTheInfo())
	}
}

fun addTransport() {
	println("Enter the class name of the transport:")
	val scan = Scanner(System.`in`)
	val className = scan.nextLine()

	try {
		val clazz = Class.forName("hummel.$className")
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

fun searchByName() {
	println("Enter the name of the transport:")
	val scan = Scanner(System.`in`)
	val name = scan.nextLine()
	var found = false

	for (item in transport) {
		if (item.getTheName() == name) {
			println(item.getTheInfo())
			found = true
		}
	}

	if (!found) {
		println("Items not found!")
	}
}

fun searchByColor() {
	println("Enter the color of the transport:")
	val scan = Scanner(System.`in`)
	val color = scan.nextLine()
	var found = false

	for (item in transport) {
		if (item.getTheColor() == color) {
			println(item.getTheInfo())
			found = true
		}
	}

	if (!found) {
		println("Items not found!")
	}
}

fun searchByPrice() {
	println("Enter the price of the transport:")
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
		println("Items not found!")
	}
}

fun loadDefaultList(): MutableList<Transport> {
	val transport = ArrayList<Transport>()
	transport.add(BicycleAist(color = "Red"))
	transport.add(BicycleStels(color = "Red"))
	transport.add(CarVolkswagen(color = "Red"))
	transport.add(CarLada(color = "Red"))

	transport.add(BicycleAist(250, "Green"))
	transport.add(BicycleStels(350, "Green"))
	transport.add(CarVolkswagen(18500, "Green"))
	transport.add(CarLada(6500, "Green"))

	transport.add(BicycleAist(150, "Blue"))
	transport.add(BicycleStels(250, "Blue"))
	transport.add(CarVolkswagen(17500, "Blue"))
	transport.add(CarLada(5500, "Blue"))

	return transport
}