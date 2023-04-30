package kornet.utils

import kornet.WorldOfTanks
import kornet.tanks.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object StandardUtils {
	fun serialize() {
		val filename = "memory/transports.ser"
		val outputStream = FileOutputStream(filename)
		val objectOutputStream = ObjectOutputStream(outputStream)
		objectOutputStream.writeObject(WorldOfTanks.tanks)
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
			val transports: MutableList<Tank> = ArrayList()

			if (obj is List<*>) {
				obj.filterIsInstance<Tank>().forEach { transports.add(it) }
			}
			objectInputStream.close()
			fileInputStream.close()
			WorldOfTanks.tanks = transports
			println("List was deserialized")
		} catch (e: Exception) {
			WorldOfTanks.tanks = loadDefaultList()
			println("Error. Default list is loaded")
		}
	}

	fun loadDefaultList(): MutableList<Tank> {
		val tanks = ArrayList<Tank>()
		tanks.add(TankLight())
		tanks.add(TankMedium())
		tanks.add(TankHeavy())

		tanks.add(TankSuperLight())
		tanks.add(TankSuperMedium())
		tanks.add(TankSuperHeavy())
		return tanks
	}
}