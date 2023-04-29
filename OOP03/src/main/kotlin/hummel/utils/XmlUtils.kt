package hummel.utils

import hummel.Shop
import hummel.transport.Transport
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

object XmlUtils {
	fun serialize(shop: Shop) {
		val context: JAXBContext = JAXBContext.newInstance(Transport::class.java, Shop::class.java)
		val marshaller = context.createMarshaller()
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
		marshaller.marshal(shop, File("memory/transports.xml"))
		println("List was serialized")
	}

	fun deserialize(shop: Shop) {
		try {
			val context = JAXBContext.newInstance(Transport::class.java, Shop::class.java)
			val unmarshaller = context.createUnmarshaller()
			val unpackedShop = unmarshaller.unmarshal(File("memory/transports.xml")) as Shop
			shop.transport = unpackedShop.transport
			println("List was deserialized")
		} catch (e: Exception) {
			shop.transport = StandardUtils.loadDefaultList()
			println("Error. Default list is loaded")
		}
	}
}
