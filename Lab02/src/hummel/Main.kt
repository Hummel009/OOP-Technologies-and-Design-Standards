package hummel

import java.util.*

val transport: MutableList<Transport> = ArrayList()

fun main() {
	val scan = Scanner(System.`in`)

	loop@ while (true) {
		println("Enter the function:")
		when (scan.nextLine()) {
			"price" -> {
				searchByPrice()
			}

			"color" -> {
				searchByColor()
			}

			"name" -> {
				searchByName()
			}

			"clear" -> {
				transport.clear()
			}

			"show" -> {
				show()
			}

			"load" -> {
				transport.addAll(loadDefaultList())
			}

			"sell" -> {
				sell()
			}

			"exit" -> break@loop
		}
	}
}

fun sell() {
	println("Enter the name of the transport")
	val scan = Scanner(System.`in`)
	val name = scan.nextLine()

	val className = Class.forName("hummel.$name")

	println("Enter the price of the transport")
	val price = scan.nextLine().toInt()
	println("Enter the color of the transport")
	val color = scan.nextLine()

	val obj = className.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport

	transport.add(obj)
}

fun show() {
	for (car in transport) {
		println(car.getTheInfo())
	}
}

fun searchByName() {
	println("Enter the name of the transport")
	val scan = Scanner(System.`in`)
	val str = scan.nextLine()
	var found = false

	for (car in transport) {
		if (car.getTheName() == str) {
			println(car.getTheInfo())
			found = true
		}
	}

	if (!found) {
		println("No info found")
	}
}

fun searchByColor() {
	println("Enter the color of the transport")
	val scan = Scanner(System.`in`)
	val str = scan.nextLine()
	var found = false

	for (car in transport) {
		if (car.getTheColor() == str) {
			println(car.getTheInfo())
			found = true
		}
	}

	if (!found) {
		println("No info found")
	}
}

fun searchByPrice() {
	println("Enter the price of the transport")
	val scan = Scanner(System.`in`)
	val price = scan.nextInt()
	var found = false

	for (car in transport) {
		if (car.getThePrice() == price) {
			println(car.getTheInfo())
			found = true
		}
	}

	if (!found) {
		println("No info found")
	}
}

fun loadDefaultList(): MutableList<Transport> {
	val transports = ArrayList<Transport>()
	transports.add(BicycleAist(color = "Red"))
	transports.add(BicycleStels(color = "Red"))
	transports.add(CarVolkswagen(color = "Red"))
	transports.add(CarLada(color = "Red"))

	transports.add(BicycleAist(250, "Green"))
	transports.add(BicycleStels(350, "Green"))
	transports.add(CarVolkswagen(18500, "Green"))
	transports.add(CarLada(6500, "Green"))

	transports.add(BicycleAist(150, "Blue"))
	transports.add(BicycleStels(250, "Blue"))
	transports.add(CarVolkswagen(17500, "Blue"))
	transports.add(CarLada(5500, "Blue"))

	return transports
}