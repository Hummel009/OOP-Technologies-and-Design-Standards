package com.github.hummel.ooptds.lab3.transport

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlSeeAlso

@XmlSeeAlso(
	BicycleAist::class,
	BicycleStels::class,
	CarLada::class,
	CarLadaImproved::class,
	CarVolkswagen::class,
	CarVolkswagenImproved::class
)
@XmlAccessorType(XmlAccessType.FIELD)
open class Transport(var price: Int = 0, var color: String = "Color", var name: String = "Name") : Serializable {
	private val serialVersionUID = 1L

	open fun getTheInfo(): String = "$name ($color): $price$"
}