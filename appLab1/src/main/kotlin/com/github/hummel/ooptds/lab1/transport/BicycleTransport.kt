package com.github.hummel.ooptds.lab1.transport

open class BicycleTransport(private var name: String) : Transport() {
	override fun getTheName(): String = name
}