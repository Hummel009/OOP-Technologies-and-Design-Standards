package com.github.hummel.ooptds.lab3.utils

import com.github.hummel.ooptds.lab3.Shop
import com.github.hummel.ooptds.lab3.transport.Transport
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

object XmlUtils {
	fun serialize() {
		val context = JAXBContext.newInstance(Transport::class.java, Shop::class.java)
		val marshaller = context.createMarshaller()
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
		marshaller.marshal(Shop, File("memory/transports.xml"))
		println("List was serialized.")
	}

	fun deserialize() {
		Shop.transport.clear()
		try {
			val context = JAXBContext.newInstance(Transport::class.java, Shop::class.java)
			val unmarshaller = context.createUnmarshaller()
			unmarshaller.unmarshal(File("memory/transports.xml")) as Shop
			println("List was deserialized.")
		} catch (e: Exception) {
			Shop.transport.addAll(StandardUtils.defaultList)
			println("Error! Default list is loaded.")
		}
	}
}
