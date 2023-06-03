package hummel.util

import hummel.transport.*

object StandardUtils {
	fun getDefaultList(): MutableList<Transport> {
		return arrayListOf(
			BicycleAist(color = "Red"),
			BicycleStels(color = "Red"),
			CarVolkswagen(color = "Red"),
			CarLada(color = "Red"),

			BicycleAist(250, "Green"),
			BicycleStels(350, "Green"),
			CarVolkswagen(18500, "Green"),
			CarLada(6500, "Green"),

			BicycleAist(150, "Blue"),
			BicycleStels(250, "Blue"),
			CarVolkswagen(17500, "Blue"),
			CarLada(5500, "Blue")
		)
	}
}