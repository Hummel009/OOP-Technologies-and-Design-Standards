package hummel

import hummel.special.*
import hummel.transport.*
import hummel.utils.JsonUtils
import hummel.utils.StandardUtils
import java.io.File
import java.net.URLClassLoader
import java.nio.charset.StandardCharsets
import java.util.*

fun main() {
	val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
	Shop.init()

	loop@ while (true) {
		println("Enter the command:")
		val command = scanner.nextLine()

		Shop.functions[command]?.invoke()

		if (command == "exit") {
			break@loop
		}
	}

	scanner.close()
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
		return CarLada(color = "Purple")
	}
}

object Shop {
	val functions: MutableMap<String, () -> Unit> = HashMap()
	val transport: MutableList<Transport> = ArrayList()
	var plugin: String = ""

	fun init() {
		functions.clear()
		functions["commands"] = this::showAllCommands
		functions["show"] = this::showAllTransport
		functions["sell"] = this::addTransport
		functions["edit"] = this::editTransport
		functions["search"] = this::searchForTransport
		functions["plugin"] = this::loadPlugin
		functions["test"] = this::testPatterns
		functions["clear"] = { transport.clear() }
		functions["load"] = { transport.addAll(StandardUtils.defaultList) }
		functions["deserialize"] = { JsonUtils.deserialize() }
		functions["serialize"] = { JsonUtils.serialize() }

		if (plugin != "") {
			try {
				val pluginFile = File(plugin)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				val clazz = classLoader.loadClass("plugin.Loader")
				val loadMethod = clazz.getDeclaredMethod("load")
				val loaderInstance = clazz.newInstance()
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
		val scanner = Scanner(System.`in`, StandardCharsets.UTF_8.name())
		plugin = scanner.nextLine()
		scanner.close()
		init()
	}

	private fun showAllCommands() {
		for (item in functions.keys) {
			println(item)
		}
	}

	private fun showAllTransport() {
		val e = transport.iterator()
		while (e.hasNext()) {
			val it = e.next()
			println(it.getTheInfo())
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
			item as CarBasic
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
		val clazz = StandardUtils.accessClass("hummel.transport.$className", "plugin.$className")

		if (clazz != null) {
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
					item as CarBasic
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
					item as CarBasic
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
					item as CarBasic
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