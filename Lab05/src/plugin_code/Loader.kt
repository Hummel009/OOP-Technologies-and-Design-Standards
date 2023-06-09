package plugin_code

import hummel.Shop
import java.util.*

class Loader {
	fun load() {
		Shop.functions["encode"] = this::encode
		Shop.functions["decode"] = this::decode
	}

	private fun decode() {
		println("Enter the key:")
		val scan = Scanner(System.`in`)
		val key = scan.nextLine().filter { it in "01" }

		if (key.length == 34) {
			val encoder = Encoder()
			encoder.encode(intArrayOf(34, 15, 14, 1), key, "memory/transports.json", "memory/transports.json")
			encoder.encode(intArrayOf(34, 15, 14, 1), key, "memory/transports.xml", "memory/transports.xml")
		} else {
			println("Wrong key. It should contain 0 and 1 only")
		}
	}

	private fun encode() {
		val length = 34
		val charset = "01"
		val random = Random()

		val sb = StringBuilder()
		for (i in 0 until length) {
			val index = random.nextInt(charset.length)
			val randomChar = charset[index]
			sb.append(randomChar)
		}

		val key = sb.toString()
		println("Random key: $key")

		val encoder = Encoder()
		encoder.encode(intArrayOf(34, 15, 14, 1), key, "memory/transports.json", "memory/transports.json")
		encoder.encode(intArrayOf(34, 15, 14, 1), key, "memory/transports.xml", "memory/transports.xml")
	}
}