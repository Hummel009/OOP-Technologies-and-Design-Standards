package com.github.hummel.ooptds.plugin

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
		val resBytes = srcBytes.copyOf()

		val currKey = StringBuilder()
		srcBytes.forEachIndexed { i, byte ->
			bufSrcFile.append(Integer.toBinaryString(byte.toInt() and 0xFF).format("%8s", "0") + " ")

			repeat(bites) { currKey.append(reg.generateKeyBit()) }

			var keyByte = 0

			"$currKey".indices.asSequence().map { j ->
				2.0.pow((bites - 1 - j)).toInt().toByte() * ("$currKey"[j].digitToInt()).toByte()
			}.forEach {
				keyByte = (keyByte + it)
			}

			bufGenkey.append("$currKey")
			resBytes[i] = byte xor keyByte.toByte()
			bufResFile.append(Integer.toBinaryString(resBytes[i].toInt() and 0xFF).format("%8s", "0") + " ")

			currKey.clear()
		}
		File(pathToResFile).writeBytes(resBytes)
	}
}