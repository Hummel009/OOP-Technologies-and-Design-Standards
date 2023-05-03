package hummel.utils

import hummel.Shop
import hummel.transport.Transport
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

object XmlUtils {
	fun serialize() {
		val context: JAXBContext = JAXBContext.newInstance(Transport::class.java, Shop::class.java)
		val marshaller = context.createMarshaller()
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
		marshaller.marshal(Shop, File("memory/transports.xml"))
		println("List was serialized")
	}

	fun deserialize() {
		try {
			val context = JAXBContext.newInstance(Transport::class.java, Shop::class.java)
			val unmarshaller = context.createUnmarshaller()
			val unpackedShop = unmarshaller.unmarshal(File("memory/transports.xml")) as Shop
			Shop.transport = unpackedShop.transport
			println("List was deserialized")
		} catch (e: Exception) {
			Shop.transport = StandardUtils.loadDefaultList()
			println("Error. Default list is loaded")
		}
	}
}
