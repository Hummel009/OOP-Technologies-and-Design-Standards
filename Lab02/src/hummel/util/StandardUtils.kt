package hummel.util

import hummel.transport.*
import java.util.ArrayList

object StandardUtils {
	fun loadDefaultList(): MutableList<Transport> {
		val transport = ArrayList<Transport>()
		transport.add(BicycleAist(color = "Red"))
		transport.add(BicycleStels(color = "Red"))
		transport.add(CarVolkswagen(color = "Red"))
		transport.add(CarLada(color = "Red"))

		transport.add(BicycleAist(250, "Green"))
		transport.add(BicycleStels(350, "Green"))
		transport.add(CarVolkswagen(18500, "Green"))
		transport.add(CarLada(6500, "Green"))

		transport.add(BicycleAist(150, "Blue"))
		transport.add(BicycleStels(250, "Blue"))
		transport.add(CarVolkswagen(17500, "Blue"))
		transport.add(CarLada(5500, "Blue"))

		return transport
	}
}