package com.github.hummel.ooptds.lab1.transport

class BicycleStels(private var color: String) : BicycleTransport("Stels") {
	private var price = 300

	constructor(i: Int, color: String) : this(color) {
		price = i
	}

	override fun getThePrice(): Int = price

	override fun getTheColor(): String = color

	override fun getTheInfo(): String = getTheName() + " (" + getTheColor() + "): " + getThePrice() + "$"
}