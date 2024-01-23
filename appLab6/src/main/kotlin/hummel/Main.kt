package hummel

import hummel.special.*
import hummel.transport.CarBasic
import hummel.transport.CarLada
import hummel.transport.CarVolkswagen
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader

fun main() {
	Shop.init()
	loop@ while (true) {
		print("Enter the command: ")
		val command = readln()

		if (command == "exit") {
			break@loop
		}

		Shop.functions[command]?.invoke() ?: println("Unknown command!")
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
		car?.let {
			availableCars.remove(it)
			return@acquire it
		} ?: run {
			val newCar = createNewCar()
			allCars.add(newCar)
			return@acquire newCar
		}
	}

	fun release(car: CarLada) {
		if (allCars.contains(car)) {
			availableCars.add(car)
		}
	}

	private fun createNewCar(): CarLada = CarLada(color = "Purple")
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	val transport: MutableList<Transport> = ArrayList()
	var plugin: String? = null

	fun init() {
		functions.clear()
		functions["commands"] = ::showAllCommands
		functions["show"] = ::showAllTransport
		functions["sell"] = ::addTransport
		functions["edit"] = ::editTransport
		functions["search"] = ::searchForTransport
		functions["plugin"] = ::loadPlugin
		functions["test"] = ::testPatterns
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }

		plugin?.let {
			try {
				val pluginFile = File(it)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				val clazz = classLoader.loadClass("plugin.Loader")
				val loadMethod = clazz.getDeclaredMethod("load")
				val loaderInstance = clazz.getConstructor().newInstance()
				loadMethod.invoke(loaderInstance)
			} catch (e: Exception) {
				println("This plugin don't exist or has no new functions. That's ok, if plugin contains only objects.")
			}
		}
	}

	private fun testPatterns() {
		val carFactory = FunctionalFactory()
		val vw = carFactory.createCar(15000, "Lime", "CarVolkswagen")
		transport.add(vw)

		val car1 = LadaPool.acquire()
		val car2 = LadaPool.acquire()
		val car3 = LadaPool.acquire()

		println(car1)
		println(car2)
		println(car3)
		LadaPool.release(car3)
		LadaPool.release(car2)
		LadaPool.release(car1)
		val car5 = LadaPool.acquire()
		val car6 = LadaPool.acquire()
		val car7 = LadaPool.acquire()
		println(car5)
		println(car6)
		println(car7)

		val visitor = FunctionalVisitor()
		car5.accept(visitor)
		car6.accept(visitor)
		car7.accept(visitor)

		val listener = Listener { car, newSpeed -> println("${car.name} is now driving at $newSpeed km/h.") }

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
		print("Enter the name of the plugin: ")
		plugin = readln()
		init()
	}

	private fun showAllCommands() {
		functions.keys.forEach { println(it) }
	}

	private fun showAllTransport() {
		transport.forEach { println(it.getTheInfo()) }
	}

	private fun editTransport() {
		val arr = transport.toTypedArray()
		arr.forEachIndexed { i, item -> println("$i. ${item.getTheInfo()}") }
		print("Enter the number of the transport to edit: ")
		val index = readIntSafe()

		if (index in arr.indices) {
			val item = arr[index]
			print("Enter the new price: ")
			val price = readIntSafe()
			print("Enter the new color: ")
			val color = readln()
			item as CarBasic
			item.price = price
			item.color = color
			if (item is Improvable) {
				print("Enter the new improvement: ")
				val improvement = readln()
				item.setImprovement(improvement)
			}
		} else {
			println("Wrong index!")
		}
	}

	private fun addTransport() {
		print("Enter the class name of the transport: ")
		val className = readln()
		val clazz = StandardUtils.accessClass("hummel.transport.$className", "plugin.$className")
		clazz?.let {
			print("Enter the price of the transport: ")
			val price = readIntSafe()
			print("Enter the color of the transport: ")
			val color = readln()
			val item = it.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport
			if (item is Improvable) {
				print("Enter the improvement of the transport: ")
				val improvement = readln()
				item.setImprovement(improvement)
			}
			transport.add(item)
		}
	}

	private fun searchForTransport() {
		print("Enter the type of the search (name, price, color): ")
		val type = readln()
		var found = false
		when (type) {
			"name" -> {
				print("Enter the name of the transport: ")
				val name = readln()
				transport.asSequence().filter { (it as CarBasic).name == name }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}

			"price" -> {
				print("Enter the price of the transport: ")
				val price = readIntSafe()
				transport.asSequence().filter { (it as CarBasic).price == price }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}

			"color" -> {
				print("Enter the color of the transport: ")
				val color = readln()
				transport.asSequence().filter { (it as CarBasic).color == color }.forEach {
					println(it.getTheInfo())
					found = true
				}
			}
		}

		if (!found) {
			println("Items not found!")
		}
	}
}

fun readIntSafe(): Int {
	return try {
		readln().toInt()
	} catch (e: Exception) {
		print("Error! Enter the correct value: ")
		readIntSafe()
	}
}