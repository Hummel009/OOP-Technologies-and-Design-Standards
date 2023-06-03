package hummel.utils

import hummel.Shop
import hummel.transport.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object StandardUtils {
	fun serialize() {
		val outputStream = FileOutputStream("memory/transports.ser")
		val objectOutputStream = ObjectOutputStream(outputStream)
		objectOutputStream.writeObject(Shop.transport)
		objectOutputStream.close()
		outputStream.close()
		println("List was serialized.")
	}

	fun deserialize() {
		try {
			val fileInputStream = FileInputStream("memory/transports.ser")
			val objectInputStream = ObjectInputStream(fileInputStream)
			val shop = objectInputStream.readObject()
			val transport = ArrayList<Transport>()

			if (shop is List<*>) {
				shop.filterIsInstance<Transport>().forEach { transport.add(it) }
			}
			objectInputStream.close()
			fileInputStream.close()
			Shop.transport = transport
			println("List was deserialized.")
		} catch (e: Exception) {
			Shop.transport = loadDefaultList()
			println("Error! Default list is loaded.")
		}
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