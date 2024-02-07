package com.github.hummel.ooptds.lab5.optional

import com.github.hummel.ooptds.lab5.transport.Transport

interface Improvable {
	fun getImprovement(): String
	fun setImprovement(improvement: String): Transport
}