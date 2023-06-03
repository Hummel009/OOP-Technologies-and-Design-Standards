package hummel.utils

import hummel.Shop
import hummel.transport.*
import java.io.File
import java.net.URLClassLoader

object StandardUtils {
	fun accessClass(idePath: String, jarPath: String): Class<*>? {
		var clazz: Class<*>? = null

		try {
			clazz = Class.forName(idePath)
		} catch (e: Exception) {
			println("Searching for the jar file...")
			try {
				val pluginFile = File(Shop.plugin)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				clazz = classLoader.loadClass(jarPath)
			} catch (e: Exception) {
				println("Class not found!")
			}
		}
		return clazz
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

		transport.add(CarVolkswagenImproved(19000, "Grey").setImprovement("Chromed Sportline"))
		transport.add(CarLadaImproved(7000, "Violet").setImprovement("Sedan Baklazhan"))

		return transport
	}
}