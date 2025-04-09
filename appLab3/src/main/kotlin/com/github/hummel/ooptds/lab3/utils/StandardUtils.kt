package com.github.hummel.ooptds.lab3.utils

import com.github.hummel.ooptds.lab3.Shop
import com.github.hummel.ooptds.lab3.transport.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
		} catch (_: Exception) {
			Shop.transport.addAll(defaultList)
			println("Error! Default list is loaded.")
		}
	}
}