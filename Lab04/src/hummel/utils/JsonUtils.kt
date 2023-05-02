package hummel.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import hummel.Shop
import hummel.optional.Editable
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
			jsonObject.addProperty("price", item.getThePrice())
			jsonObject.addProperty("color", item.getTheColor())
			jsonObject.addProperty("className", item.javaClass.name)
			if (item is Improvable) {
				jsonObject.addProperty("improvement", item.getTheImprovement())
			}
			return jsonObject
		}
	}

	fun serialize(shop: Shop) {
		val gson: Gson =
			GsonBuilder().registerTypeHierarchyAdapter(Transport::class.java, TransportJsonSerializer()).setPrettyPrinting().create()
		val type: TypeToken<MutableList<Transport>> = object : TypeToken<MutableList<Transport>>() {}
		val file = File("memory/transports.json")
		val json = gson.toJson(shop.transport, type.type)
		val writer = FileWriter(file)
		writer.use {
			it.write(json)
		}
		println("List was serialized")
	}

	fun deserialize(shop: Shop) {
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
					val className = try {
						Class.forName(name)
					} catch (e: Exception) {
						val pluginFile = File(shop.plugin)
						val classLoader = URLClassLoader(arrayOf(pluginFile.toURI().toURL()))
						classLoader.loadClass(name)//TensorFlow
					}
					val obj = className.newInstance() as Transport
					obj as Editable
					obj.setTheColor(color)
					obj.setThePrice(price)

					if (className == CarVolkswagenImproved::class.java || className == CarLadaImproved::class.java) {
						val improvement = item.get("improvement").asString
						obj as Improvable
						obj.setTheImprovement(improvement)
					}
					transports.add(obj)
				}
				shop.transport = transports
				println("List was deserialized")
			}
		} catch (e: Exception) {
			shop.transport = StandardUtils.loadDefaultList()
			println("Error. Default list is loaded")
		}
	}
}