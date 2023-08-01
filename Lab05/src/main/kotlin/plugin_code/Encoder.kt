package plugin_code

import java.io.File
import kotlin.experimental.xor
import kotlin.math.pow


class Encoder {
	private val bites = 8
	private var bufSrcFile = StringBuilder()
	private var bufResFile = StringBuilder()
	private var bufGenkey = StringBuilder()

	fun encode(
		polynomialPowers: IntArray, initialKey: String, pathToSrcFile: String, pathToResFile: String
	) {
		val reg = Register(polynomialPowers, initialKey)

		val srcBytes = File(pathToSrcFile).readBytes()

		for (i in srcBytes.indices) {
			bufSrcFile.append(Integer.toBinaryString(srcBytes[i].toInt() and 0xFF).format("%8s", "0") + " ")

			val currKey = StringBuilder()
			repeat(bites) {
				currKey.append(reg.generateKeyBit())
			}

			var keyByte = 0.toByte()

			for (j in currKey.toString().indices) {
				val bp = (currKey.toString()[j].digitToInt()).toByte() * 2.0.pow((bites - 1 - j)).toInt().toByte()
				keyByte = (keyByte + bp).toByte()
			}

			bufGenkey.append(currKey.toString())
			srcBytes[i] = srcBytes[i] xor keyByte
			bufResFile.append(Integer.toBinaryString(srcBytes[i].toInt() and 0xFF).format("%8s", "0") + " ")
		}
		File(pathToResFile).writeBytes(srcBytes)
	}
}