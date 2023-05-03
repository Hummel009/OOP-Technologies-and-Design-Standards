package hummel.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import hummel.Shop
import hummel.transport.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.net.URLClassLoader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object StandardUtils {
	fun reflectAccess(path1: String, path2: String): Class<*>? {
		var className: Class<*>? = null

		try {
			className = Class.forName(path1)
		} catch (e: Exception) {
			println("Searching for the plugin")
			try {
				val pluginFile = File(Shop.plugin)
				val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
				className = classLoader.loadClass(path2)
			} catch (e: Exception) {
				println("The content wasn't found")
			}
		}
		return className
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

		transports.add(CarVolkswagenImproved(19000, "Grey").setImprovement("Chromed Sportline"))
		transports.add(CarLadaImproved(7000, "Violet").setImprovement("Sedan Baklazhan"))

		return transports
	}
}