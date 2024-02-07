package com.github.hummel.ooptds.lab3.optional

import com.github.hummel.ooptds.lab3.transport.Transport

interface Improvable {
	fun getImprovement(): String
	fun setImprovement(improvement: String): Transport
}