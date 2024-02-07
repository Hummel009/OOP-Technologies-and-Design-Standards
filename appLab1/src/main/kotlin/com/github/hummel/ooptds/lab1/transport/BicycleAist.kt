package com.github.hummel.ooptds.lab1.transport

class BicycleAist(private var color: String) : BicycleTransport("Aist") {
	private var price = 100

	constructor(i: Int, color: String) : this(color) {
		price = i
	}

	override fun getThePrice(): Int = price

	override fun getTheColor(): String = color

	override fun getTheInfo(): String = getTheName() + " (" + getTheColor() + "): " + getThePrice() + "$"
}
