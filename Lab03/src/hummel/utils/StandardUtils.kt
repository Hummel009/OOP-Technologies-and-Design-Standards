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
		Shop.transport.clear()
		try {
			val fileInputStream = FileInputStream("memory/transports.ser")
			val objectInputStream = ObjectInputStream(fileInputStream)
			val shop = objectInputStream.readObject()

			if (shop is List<*>) {
				shop.filterIsInstance<Transport>().forEach { Shop.transport.add(it) }
			}
			objectInputStream.close()
			fileInputStream.close()
			println("List was deserialized.")
		} catch (e: Exception) {
			Shop.transport.addAll(getDefaultList())
			println("Error! Default list is loaded.")
		}
	}

	fun getDefaultList(): MutableList<Transport> {
		return arrayListOf(
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
	}
}