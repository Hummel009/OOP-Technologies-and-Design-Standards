package com.github.hummel.ooptds.lab3.transport

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlSeeAlso
import java.io.Serializable

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
	open fun getTheInfo(): String = "$name ($color): $price$"
}