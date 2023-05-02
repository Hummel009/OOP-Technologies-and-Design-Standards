package hummel.optional

import hummel.transport.Transport

interface Improvable {
	fun getImprovement(): String
	fun setImprovement(improvement: String): Transport
}