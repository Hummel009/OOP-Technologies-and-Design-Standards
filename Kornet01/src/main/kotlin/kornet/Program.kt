package kornet

import kornet.tanks.FirstnationTank
import kornet.tanks.HeavyTank
import kornet.tanks.SuperHeavyTank
import kornet.tanks.Tank
import java.util.*

var tanks = ArrayList<Tank>()

fun main() {
    loadDefaultList()
    val scan = Scanner(System.`in`)

    loop@ while (true) {
        println("Enter the command: ")
        when (scan.nextLine()) {
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