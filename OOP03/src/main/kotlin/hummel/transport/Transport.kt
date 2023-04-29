package hummel.transport

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
open class Transport : Serializable {
	private val serialVersionUID = 1L

	open fun getTheColor(): String {
		return "no"
	}

	open fun getTheName(): String {
		return "NULL"
	}

	open fun getThePrice(): Int {
		return 0
	}

	open fun getTheInfo(): String {
		return "${getTheName()} (${getTheColor()}): ${getThePrice()}$"
	}
}