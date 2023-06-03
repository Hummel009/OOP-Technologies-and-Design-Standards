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
	transport.add(BicycleAist("Red", 150))
	transport.add(BicycleStels("Red"))
	transport.add(CarVolkswagen("Red", 18500))
	transport.add(CarLada("Red"))

	transport.add(BicycleAist("Green"))
	transport.add(BicycleStels("Green", 350))
	transport.add(CarVolkswagen("Green"))
	transport.add(CarLada("Green"))

	transport.add(BicycleAist("Blue"))
	transport.add(BicycleStels("Blue"))
	transport.add(CarVolkswagen("Blue"))
	transport.add(CarLada("Blue", 5500))

	return transport
}