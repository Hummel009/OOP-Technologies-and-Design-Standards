package com.github.hummel.ooptds.lab4.utils

import com.github.hummel.ooptds.lab4.Shop
import com.github.hummel.ooptds.lab4.optional.Improvable
import com.github.hummel.ooptds.lab4.transport.CarLadaImproved
import com.github.hummel.ooptds.lab4.transport.CarVolkswagenImproved
import com.github.hummel.ooptds.lab4.transport.Transport
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type

object JsonUtils {
	class Serializer : JsonSerializer<Transport> {
		override fun serialize(item: Transport, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
			val jsonObject = JsonObject()
			jsonObject.addProperty("price", item.price)
			jsonObject.addProperty("color", item.color)
			jsonObject.addProperty("className", item.javaClass.name)
			if (item is Improvable) {
				jsonObject.addProperty("improvement", item.getImprovement())
			}
			return jsonObject
		}
	}

	fun serialize() {
		val gson =
			GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, Serializer()).setPrettyPrinting().create()
		val type = object : TypeToken<MutableList<Transport>>() {}
		val file = File("memory/transports.json")
		val json = gson.toJson(Shop.transport, type.type)
		val writer = FileWriter(file)
		writer.use { it.write(json) }
		println("List was serialized.")
	}

	fun deserialize() {
		val gson =
			GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, Serializer()).setPrettyPrinting().create()
		Shop.transport.clear()
		try {
			val file = File("memory/transports.json")
			val reader = FileReader(file)
			reader.use {
				val json = gson.fromJson(it, JsonArray::class.java)
				for (element in json) {
					val jsonObject = element.asJsonObject
					val price = jsonObject.get("price").asInt
					val color = jsonObject.get("color").asString
					val className = jsonObject.get("className").asString
					val clazz = StandardUtils.accessClass(className, className)
					if (clazz != null) {
						val item = clazz.getConstructor(Int::class.java, String::class.java)
							.newInstance(price, color) as Transport
						if (clazz == CarVolkswagenImproved::class.java || clazz == CarLadaImproved::class.java) {
							val improvement = jsonObject.get("improvement").asString
							item as Improvable
							item.setImprovement(improvement)
						}
						Shop.transport.add(item)
					}
				}
				println("List was deserialized.")
			}
		} catch (_: Exception) {
			Shop.transport.addAll(StandardUtils.defaultList)
			println("Error! Default list is loaded.")
		}
	}
}