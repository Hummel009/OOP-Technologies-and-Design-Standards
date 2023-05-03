package hummel.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import hummel.Shop
import hummel.optional.Improvable
import hummel.transport.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type
import java.net.URLClassLoader

object JsonUtils {
	class TransportJsonSerializer : JsonSerializer<Transport> {
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
		val gson: Gson =
			GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, TransportJsonSerializer())
				.setPrettyPrinting().create()
		val type: TypeToken<MutableList<Transport>> = object : TypeToken<MutableList<Transport>>() {}
		val file = File("memory/transports.json")
		val json = gson.toJson(Shop.transport, type.type)
		val writer = FileWriter(file)
		writer.use {
			it.write(json)
		}
		println("List was serialized")
	}

	fun deserialize() {
		val gson: Gson =
			GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, TransportJsonSerializer()).create()

		try {
			val file = File("memory/transports.json")
			val reader = FileReader(file)
			reader.use {
				val json = gson.fromJson(it, JsonArray::class.java)
				val transports: MutableList<Transport> = ArrayList()
				for (element in json) {
					val item = element.asJsonObject
					val price = item.get("price").asInt
					val color = item.get("color").asString
					val name = item.get("className").asString
					val className = Class.forName(name)
					val obj =
						className.getConstructor(Int::class.java, String::class.java).newInstance(price, color) as Transport

					if (className == CarVolkswagenImproved::class.java || className == CarLadaImproved::class.java) {
						val improvement = item.get("improvement").asString
						obj as Improvable
						obj.setImprovement(improvement)
					}
					transports.add(obj)
				}
				Shop.transport = transports
				println("List was deserialized")
			}
		} catch (e: Exception) {
			Shop.transport = StandardUtils.loadDefaultList()
			println("Error. Default list is loaded")
		}
	}
}