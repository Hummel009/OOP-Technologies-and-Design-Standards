package kornet.utils

import kornet.WorldOfTanks
import kornet.tanks.Tank
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

object XmlUtils {
	fun serialize() {
		val context: JAXBContext = JAXBContext.newInstance(Tank::class.java, WorldOfTanks::class.java)
		val marshaller = context.createMarshaller()
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
		marshaller.marshal(WorldOfTanks, File("memory/transports.xml"))
		println("List was serialized")
	}

	fun deserialize() {
		try {
			val context = JAXBContext.newInstance(Tank::class.java, WorldOfTanks::class.java)
			val unmarshaller = context.createUnmarshaller()
			val unpackedShop = unmarshaller.unmarshal(File("memory/transports.xml")) as WorldOfTanks
			WorldOfTanks.tanks = unpackedShop.tanks
			println("List was deserialized")
		} catch (e: Exception) {
			WorldOfTanks.tanks = StandardUtils.loadDefaultList()
			println("Error. Default list is loaded")
		}
	}
}
