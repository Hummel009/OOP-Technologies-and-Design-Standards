package hummel.utils

import hummel.Shop
import hummel.transport.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object StandardUtils {
	fun serialize() {
		val filename = "memory/transports.ser"
		val outputStream = FileOutputStream(filename)
		val objectOutputStream = ObjectOutputStream(outputStream)
		objectOutputStream.writeObject(Shop.transport)
		objectOutputStream.close()
		outputStream.close()
		println("List was serialized")
	}

	fun deserialize() {
		try {
			val filename = "memory/transports.ser"
			val fileInputStream = FileInputStream(filename)
			val objectInputStream = ObjectInputStream(fileInputStream)
			val obj = objectInputStream.readObject()
			val transports: MutableList<Transport> = ArrayList()

			if (obj is List<*>) {
				obj.filterIsInstance<Transport>().forEach { transports.add(it) }
			}
			objectInputStream.close()
			fileInputStream.close()
			Shop.transport = transports
			println("List was deserialized")
		} catch (e: Exception) {
			Shop.transport = loadDefaultList()
			println("Error. Default list is loaded")
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

		transports.add(CarVolkswagenImproved(19000, "Grey").setImprovement("Chromed Sportline"))
		transports.add(CarLadaImproved(7000, "Violet").setImprovement("Sedan Baklazhan"))

		return transports
	}
}