package hummel.transport

open class Transport {
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