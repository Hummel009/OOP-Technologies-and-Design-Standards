package com.github.hummel.ooptds.lab1.transport

class CarLada(private var color: String) : CarTransport("Lada") {
	private var price = 5000

	constructor(i: Int, color: String) : this(color) {
		price = i
	}

	override fun getThePrice(): Int = price

	override fun getTheColor(): String = color

	override fun getTheInfo(): String = getTheName() + " (" + getTheColor() + "): " + getThePrice() + "$"
}