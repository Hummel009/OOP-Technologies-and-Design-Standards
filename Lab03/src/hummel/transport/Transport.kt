package hummel.transport

import java.io.Serializable
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType

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
	
	open fun getTheInfo(): String {
		return "$name ($color): $price$"
	}
}