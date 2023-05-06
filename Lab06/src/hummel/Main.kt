package hummel

import hummel.special.*
import hummel.transport.*
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.util.*

fun main() {
	val scan = Scanner(System.`in`)
	Shop.init()

	loop@ while (true) {
		println("Enter the function:")
		val command = scan.nextLine()

		Shop.functions[command]?.invoke()

		if (command == "exit") {
			break@loop
		}
	}
}

object LadaPool {
	private val availableCars = mutableListOf<CarLada>()
	private val allCars = mutableListOf<CarLada>()

	init {
		repeat(3) {
			val car = createNewCar()
			availableCars.add(car)
			allCars.add(car)
		}
	}

	fun acquire(): CarLada {
		val car = availableCars.firstOrNull()
		return if (car != null) {
			availableCars.remove(car)
			car
		} else {
			val newCar = createNewCar()
			allCars.add(newCar)
			newCar
		}
	}

	fun release(car: CarLada) {
		if (allCars.contains(car)) {
			availableCars.add(car)
		}
	}

	private fun createNewCar(): CarLada {
		return CarLada(6000, "Purple")
	}
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	var transport: MutableList<Transport> = ArrayList()
	var plugin = ""

	fun init() {
		functions.clear()
		functions["commands"] = this::showAllFunctions
		functions["show"] = this::showAllItems
		functions["sell"] = this::addItem
		functions["edit"] = this::editItem
		functions["search"] = this::searchForItem
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.loadDefaultList()) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }
		functions["plugin"] = this::loadPlugin
		functions["test"] = this::testPatterns
		functions["convertJsonXml"] = StandardUtils::convertJsonToXml
		functions["convertXmlJson"] = StandardUtils::convertXmlToJson

		if (plugin != "") {
			val className = StandardUtils.reflectAccess("$plugin.Loader", "$plugin.Loader")

			if (className != null) {
				try {
					val loadMethod = className.getDeclaredMethod("load")
					val loaderInstance = className.newInstance()
					loadMethod.invoke(loaderInstance)
				} catch (e: Exception) {
					println("This plugin has no new functions")
				}
			}
		}
	}

	private fun testPatterns() {
		val carFactory = FunctionalFactory()
		val vw = carFactory.createCar(15000, "Lime", "CarVolkswagen");
		transport.add(vw)

		val carPool = LadaPool

		val car1 = carPool.acquire()
		val car2 = carPool.acquire()
		val car3 = carPool.acquire()

		println(car1)
		println(car2)
		println(car3)
		carPool.release(car3)
		carPool.release(car2)
		carPool.release(car1)
		val car5 = carPool.acquire()
		val car6 = carPool.acquire()
		val car7 = carPool.acquire()
		println(car5)
		println(car6)
		println(car7)

		val visitor = FunctionalVisitor()
		car5.accept(visitor)
		car6.accept(visitor)
		car7.accept(visitor)

		val listener = object : Listener {
			override fun onSpeedChange(car: CarBasic, newSpeed: Int) {
				println("${car.name} is now driving at $newSpeed km/h.")
			}
		}

		car5.addSpeedChangeListener(listener)
		car6.addSpeedChangeListener(listener)
		vw.addSpeedChangeListener(listener)

		car5.speed = 60
		car6.speed = 80
		vw.speed = 100

		println("${car5.name}: " + car5.getEngine())
		vw.setEngine(EngineGas())
		println("${vw.name}: " + vw.getEngine())

		val car = CarVolkswagen(100000, "Brown")
		car.add(ComponentEngine("Electric"))
		car.add(ComponentTransmission("Single-speed"))
		car.add(ComponentSuspension("Air"))
		car.display()
	}

	private fun loadPlugin() {
		println("Enter the name of the plugin:")
		val scan = Scanner(System.`in`)
		plugin = scan.nextLine()
		init()
	}

	private fun showAllFunctions() {
		for (item in functions.keys) {
			println(item)
		}
	}

	private fun showAllItems() {
		val e = transport.iterator()
		while (e.hasNext()) {
			val it = e.next()
			println(it.getTheInfo())
		}
	}

	private fun editItem() {
		val arr = transport.toTypedArray()
		for (i in arr.indices) {
			println("$i. ${arr[i].getTheInfo()}")
		}
		println("Enter the number of the transport to edit:")
		val scan = Scanner(System.`in`)
		val id = scan.nextLine().toInt()
		if (id !in arr.indices) {
			println("Error")
		} else {
			val transport = arr[id]
			transport as CarBasic
			println("Enter the new price")
			val price = scan.nextLine().toInt()
			println("Enter the new color")
			val color = scan.nextLine()
			transport.price = price
			transport.color = color
			if (transport is Improvable) {
				println("Enter the new improvement")
				val improvement = scan.nextLine()
				transport.setImprovement(improvement)
			}
		}
	}

	private fun addItem() {
		println("Enter the name of the transport")
		val scan = Scanner(System.`in`)
		val name = scan.nextLine()
		val className = StandardUtils.reflectAccess("hummel.transport.$name", "$plugin.$name")

		if (className != null) {
			println("Enter the price of the transport")
			val price = scan.nextLine().toInt()
			println("Enter the color of the transport")
			val color = scan.nextLine()
			val obj =
				className.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as CarBasic
			if (obj is Improvable) {
				println("Enter the improvement of the transport")
				val improvement = scan.nextLine()
				obj.setImprovement(improvement)
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
					if ((item as CarBasic).name == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"price" -> {
				println("Enter the price of the transport")
				val comparing = scan.nextLine().toInt()
				transport.forEach { item ->
					if ((item as CarBasic).price == comparing) {
						println(item.getTheInfo())
						found = true
					}
				}
			}

			"color" -> {
				println("Enter the name of the transport")
				val comparing = scan.nextLine()
				transport.forEach { item ->
					if ((item as CarBasic).color == comparing) {
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