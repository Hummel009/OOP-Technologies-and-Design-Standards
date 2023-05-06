package kornet.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import kornet.WorldOfTanks
import kornet.optional.Standard
import kornet.optional.Super
import kornet.tanks.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type

object JsonUtils {
	private val standardTanks = hashSetOf(
		TankHeavy::class.java,
		TankLight::class.java,
		TankMedium::class.java
	)

	private val superTanks = hashSetOf(
		TankSuperHeavy::class.java,
		TankSuperLight::class.java,
		TankSuperMedium::class.java
	)

	class Serializer : JsonSerializer<Tank> {
		override fun serialize(item: Tank, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
			val jsonObject = JsonObject()
			jsonObject.addProperty("name", item.name)
			jsonObject.addProperty("nation", item.nation)
			jsonObject.addProperty("level", item.level)
			jsonObject.addProperty("member", item.member)
			jsonObject.addProperty("className", item.javaClass.name)
			if (item is Standard) {
				jsonObject.addProperty("standard1", item.getFirstStandardValue())
				jsonObject.addProperty("standard2", item.getSecondStandardValue())
			}
			if (item is Super) {
				jsonObject.addProperty("super1", item.getFirstSuperValue())
				jsonObject.addProperty("super2", item.getSecondSuperValue())
			}
			return jsonObject
		}
	}

	fun serialize() {
		val gson: Gson =
			GsonBuilder().registerTypeHierarchyAdapter(Tank::class.java, Serializer()).setPrettyPrinting()
				.create()
		val type: TypeToken<MutableList<Tank>> = object : TypeToken<MutableList<Tank>>() {}
		val file = File("memory/transports.json")
		val json = gson.toJson(WorldOfTanks.tanks, type.type)
		val writer = FileWriter(file)
		writer.use {
			it.write(json)
		}
		println("List was serialized")
	}

	fun deserialize() {
		val gson: Gson =
			GsonBuilder().registerTypeHierarchyAdapter(Tank::class.java, Serializer()).create()

		try {
			val file = File("memory/transports.json")
			val reader = FileReader(file)
			reader.use {
				val json = gson.fromJson(it, JsonArray::class.java)
				val transports: MutableList<Tank> = ArrayList()
				for (element in json) {
					val item = element.asJsonObject
					val name = item.get("name").asString
					val nation = item.get("nation").asString
					val level = item.get("level").asString
					val member = item.get("member").asString
					val className = item.get("className").asString
					val restoredClass = Class.forName(className)

					val obj = restoredClass.newInstance() as Tank
					obj.name = name
					obj.nation = nation
					obj.level = level
					obj.member = member

					if (standardTanks.contains(restoredClass)) {
						obj as Standard
						val standard1 = item.get("standard1").asString
						val standard2 = item.get("standard2").asString
						obj.setFirstStandardValue(standard1)
						obj.setSecondStandardValue(standard2)
					}

					if (superTanks.contains(restoredClass)) {
						obj as Super
						val super1 = item.get("super1").asString
						val super2 = item.get("super2").asString
						obj.setFirstSuperValue(super1)
						obj.setSecondSuperValue(super2)
					}

					transports.add(obj)
				}
				WorldOfTanks.tanks = transports
				println("List was deserialized")
			}
		} catch (e: Exception) {
			WorldOfTanks.tanks = StandardUtils.loadDefaultList()
			println("Error. Default list is loaded")
		}
	}
}