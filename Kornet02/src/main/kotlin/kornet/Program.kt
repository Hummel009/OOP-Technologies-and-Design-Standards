package kornet

import kornet.tanks.*
import java.util.*

var tanks = ArrayList<Tank>()

fun main() {
	loadDefaultList()
	val scan = Scanner(System.`in`)

	loop@ while (true) {
		println("Enter the command: ")
		when (scan.nextLine()) {
			"add" -> {
				addTank()
			}

			"default" -> {
				loadDefaultList()
			}

			"clear" -> {
				tanks.clear()
			}

			"show" -> {
				for (item in tanks) {
					item.printInfo()
					println()
				}
			}

			"exit" -> {
				break@loop
			}
		}
	}
}

fun addTank() {
	val scan = Scanner(System.`in`)
	println("Enter the name: ")

	val tank = when (scan.nextLine()) {
		"HeavyTank" -> {
			HeavyTank("Panzerkampfwagen ausf. V", "Deutschland", 3, 5, 500, 10.0)
		}

		"LightTank" -> {
			LightTank("Panzerkampfwagen ausf. II", "Deutschland", 3, 2, 500, 10)
		}

		"MediumTank" -> {
			MediumTank("Panzerkampfwagen ausf. IV", "Deutschland", 3, 4, 500, 10)
		}

		"SuperHeavyTank" -> {
			SuperHeavyTank("Panzerkampfwagen ausf. VI", "Deutschland", 3, 6, 500, 10.0, 46.0, 500)
		}

		"SuperLightTank" -> {
			SuperLightTank("Panzerkampfwagen ausf. I", "Deutschland", 3, 1, 500, 10, "", 10)
		}

		"SuperMediumTank" -> {
			SuperMediumTank("Panzerkampfwagen ausf. III", "Deutschland", 3, 3, 500, 10, 46, 10)
		}

		else -> {
			FirstnationTank("T-34", "USSR", 0, 3)
		}
	}
	println("Enter the number of weapons: ")
    val i = scan.nextLine().toInt()

	for (weapon in 0 until i) {
		val cal1 = Random().nextInt(99)
		val cal2 = Random().nextInt(99)
		tank.addWeapon("Калибр $cal1", cal2)
	}

	tanks.add(tank)
}

fun loadDefaultList() {
	val objheavy = SuperHeavyTank("Об. 277", "USSR", 4, 10, 490, 12.5, 55.7, 140)
	objheavy.addWeapon("M-65", 130)
	tanks.add(objheavy)

	val kv = HeavyTank("КВ-1", "USSR", 5, 5, 110, 3.84)
	kv.addWeapon("76-мм ЗиС-5", 76)
	kv.addWeapon("проект 413", 57)
	kv.addWeapon("У-11", 122)
	kv.addWeapon("Ф-30", 85)
	tanks.add(kv)

	val strv = FirstnationTank("Strv fm/21", "Sweden", 1, 2)
	strv.addWeapon("kan m/škoda", 37)
	strv.addWeapon("akan m/Madsen", 20)
	tanks.add(strv)
}