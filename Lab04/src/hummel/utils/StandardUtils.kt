package hummel.utils

import hummel.transport.*

object StandardUtils {
	fun loadDefaultList(): MutableList<Transport> {
		val transports = ArrayList<Transport>()
		transports.add(BicycleAist(color = "Red"))
		transports.add(BicycleStels(color = "Red"))
		transports.add(CarVolkswagen(color = "Red"))
		transports.add(CarLada(color = "Red"))

		transports.add(BicycleAist(250, "Green"))
		transports.add(BicycleStels(350, "Green"))
		transports.add(CarVolkswagen(18500, "Green"))
		transports.add(CarLada(6500, "Green"))

		transports.add(BicycleAist(150, "Blue"))
		transports.add(BicycleStels(250, "Blue"))
		transports.add(CarVolkswagen(17500, "Blue"))
		transports.add(CarLada(5500, "Blue"))

		transports.add(CarVolkswagenImproved(19000, "Grey", "Chromed Sportline"))
		transports.add(CarLadaImproved(7000, "Violet", "Sedan Baklazhan"))

		return transports
	}
}