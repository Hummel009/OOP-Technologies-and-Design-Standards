package com.github.hummel.ooptds.lab5.utils

import com.github.hummel.ooptds.lab5.Shop
import com.github.hummel.ooptds.lab5.transport.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
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

	fun convertXmlToJson() {
		val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		val doc = docBuilder.parse(File("memory/transports.xml"))
		doc.documentElement.normalize()

		val jsonArray = mutableListOf<JsonObject>()

		val objectNodes = doc.getElementsByTagName("object")
		for (i in 0 until objectNodes.length) {
			val objectNode = objectNodes.item(i) as Element
			val jsonObject = JsonObject()

			val propertyNodes = objectNode.childNodes
			for (j in 0 until propertyNodes.length) {
				val propertyNode = propertyNodes.item(j)
				if (propertyNode.nodeType == Node.ELEMENT_NODE) {
					val propertyName = propertyNode.nodeName
					var propertyValue: Any? = null
					when (propertyNode.childNodes.length) {
						1 -> {
							val textContent = propertyNode.textContent
							propertyValue = if (textContent == "true" || textContent == "false") {
								textContent.toBoolean()
							} else {
								try {
									textContent.toInt()
								} catch (_: NumberFormatException) {
									try {
										textContent.toDouble()
									} catch (_: NumberFormatException) {
										textContent
									}
								}
							}
						}

						else -> parseList(propertyNode)
					}
					when (propertyValue) {
						is Boolean -> jsonObject.addProperty(propertyName, propertyValue)
						is Number -> jsonObject.addProperty(propertyName, propertyValue)
						is String -> jsonObject.addProperty(propertyName, propertyValue)
					}
				}
			}

			jsonArray.add(jsonObject)
		}

		val gson = GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, JsonUtils.Serializer())
			.setPrettyPrinting().create()
		File("memory/transports.json").writeText(gson.toJson(jsonArray))
	}

	@Suppress("LoopToCallChain")
	private fun parseList(node: Node): List<Any> {
		val list = mutableListOf<Any>()

		val itemNodes = node.childNodes
		for (i in 0 until itemNodes.length) {
			val itemNode = itemNodes.item(i)
			if (itemNode.nodeType == Node.ELEMENT_NODE && itemNode.nodeName == "item") {
				val textContent = itemNode.textContent
				val itemValue = try {
					textContent.toInt()
				} catch (_: NumberFormatException) {
					try {
						textContent.toDouble()
					} catch (_: NumberFormatException) {
						textContent
					}
				}
				list.add(itemValue)
			}
		}

		return list
	}

	fun convertJsonToXml() {
		val gson = GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, JsonUtils.Serializer())
			.setPrettyPrinting().create()
		val jsonArray = gson.fromJson(File("memory/transports.json").readText(), Array<Any>::class.java)

		val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		val doc = docBuilder.newDocument()

		val rootElement = doc.createElement("root")
		doc.appendChild(rootElement)

		jsonArray.forEach { jsonObject ->
			val objectElement = doc.createElement("object")
			rootElement.appendChild(objectElement)

			jsonObject as Map<*, *>
			jsonObject.forEach { (key, value) ->
				val propertyElement = doc.createElement(key.toString())
				objectElement.appendChild(propertyElement)

				when (value) {
					is String -> propertyElement.appendChild(doc.createTextNode(value))
					is Number -> propertyElement.appendChild(doc.createTextNode("$value"))
					is Boolean -> propertyElement.appendChild(doc.createTextNode("$value"))
					is List<*> -> value.forEach { item ->
						val itemElement = doc.createElement("item")
						itemElement.appendChild(doc.createTextNode(item.toString()))
						propertyElement.appendChild(itemElement)
					}

					is Map<*, *> -> value.forEach { (subKey, subValue) ->
						val subElement = doc.createElement(subKey.toString())
						propertyElement.appendChild(subElement)

						if (subValue is List<*>) {
							subValue.forEach { item ->
								val itemElement = doc.createElement("item")
								itemElement.appendChild(doc.createTextNode(item.toString()))
								subElement.appendChild(itemElement)
							}
						} else {
							subElement.appendChild(doc.createTextNode(subValue.toString()))
						}
					}
				}
			}
		}

		val transformer = TransformerFactory.newInstance().newTransformer()
		transformer.setOutputProperty(OutputKeys.INDENT, "yes")
		transformer.setOutputProperty("indent", "yes")
		transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4")
		transformer.transform(DOMSource(doc), StreamResult(File("memory/transports.xml")))
	}

	fun accessClass(idePath: String, jarPath: String): Class<*>? {
		var clazz: Class<*>? = null

		try {
			clazz = Class.forName(idePath)
		} catch (_: Exception) {
			println("Searching for the plugin...")
			try {
				val pluginFile = Shop.plugin?.let { File(it) }
				val classLoader = URLClassLoader(arrayOf(pluginFile?.toURI()?.toURL()))
				clazz = classLoader.loadClass(jarPath)
			} catch (_: Exception) {
				println("Class not found!")
			}
		}
		return clazz
	}
}