package hummel

import java.util.*
import kotlin.collections.ArrayList

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

			"exit" -> break@loop
		}
	}
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
	transports.add(BicycleAist("Red", 150))
	transports.add(BicycleStels("Red"))
	transports.add(CarVolkswagen("Red", 18500))
	transports.add(CarLada("Red"))

	transports.add(BicycleAist("Green"))
	transports.add(BicycleStels("Green", 350))
	transports.add(CarVolkswagen("Green"))
	transports.add(CarLada("Green"))

	transports.add(BicycleAist("Blue"))
	transports.add(BicycleStels("Blue"))
	transports.add(CarVolkswagen("Blue"))
	transports.add(CarLada("Blue", 5500))

	return transports
}