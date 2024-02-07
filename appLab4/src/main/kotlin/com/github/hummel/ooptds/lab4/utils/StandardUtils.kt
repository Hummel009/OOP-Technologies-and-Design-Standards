package com.github.hummel.ooptds.lab4.utils

import com.github.hummel.ooptds.lab4.Shop
import com.github.hummel.ooptds.lab4.transport.*
import java.io.File
import java.net.URLClassLoader

object StandardUtils {
	val defaultList: ArrayList<Transport> = arrayListOf(
		BicycleAist(color = "Red"),
		BicycleStels(color = "Red"),
		CarVolkswagen(color = "Red"),
		CarLada(color = "Red"),

		BicycleAist(250, "Green"),
		BicycleStels(350, "Green"),
		CarVolkswagen(18500, "Green"),
		CarLada(6500, "Green"),

		BicycleAist(150, "Blue"),
		BicycleStels(250, "Blue"),
		CarVolkswagen(17500, "Blue"),
		CarLada(5500, "Blue"),

		CarVolkswagenImproved(19000, "Grey").setImprovement("Chromed Sportline"),
		CarLadaImproved(7000, "Violet").setImprovement("Sedan Baklazhan")
	)

	fun accessClass(idePath: String, jarPath: String): Class<*>? {
		var clazz: Class<*>? = null

		try {
			clazz = Class.forName(idePath)
		} catch (e: Exception) {
			println("Searching for the jar file...")
			try {
				val pluginFile = Shop.plugin?.let { File(it) }
				val classLoader = URLClassLoader(arrayOf(pluginFile?.toURI()?.toURL()))
				clazz = classLoader.loadClass(jarPath)
			} catch (e: Exception) {
				println("Class not found!")
			}
		}
		return clazz
	}
}