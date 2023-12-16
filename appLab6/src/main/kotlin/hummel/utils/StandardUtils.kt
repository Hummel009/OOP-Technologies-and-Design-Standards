package hummel.utils

import hummel.Shop
import hummel.special.Transport
import hummel.transport.*
import java.io.File
import java.net.URLClassLoader

object StandardUtils {
	val defaultList: ArrayList<Transport> = arrayListOf(
		CarVolkswagen(color = "Red"),
		CarLada(color = "Red"),
		CarVolkswagen(18500, "Green"),
		CarLada(6500, "Green"),
		CarVolkswagen(17500, "Blue"),
		CarLada(5500, "Blue"),
		Decorator(CarLadaImproved(15500, "Yellow").setImprovement("Sunroof") as Transport),
		Adapter(CarPeugeot()),
		CarVolkswagenImproved(19000, "Grey").setImprovement("Chromed Sportline") as CarBasic,
		CarLadaImproved(7000, "Violet").setImprovement("Sedan Baklazhan") as CarBasic
	)

	fun accessClass(idePath: String, jarPath: String): Class<*>? {
		var clazz: Class<*>? = null

		try {
			clazz = Class.forName(idePath)
		} catch (e: Exception) {
			println("Searching for the plugin...")
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
}