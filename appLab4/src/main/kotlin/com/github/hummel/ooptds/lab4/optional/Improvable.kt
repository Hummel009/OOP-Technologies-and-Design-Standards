package com.github.hummel.ooptds.lab4.optional

import com.github.hummel.ooptds.lab4.transport.Transport

interface Improvable {
	fun getImprovement(): String
	fun setImprovement(improvement: String): Transport
}