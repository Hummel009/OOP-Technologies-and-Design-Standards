package hummel

open class BicycleTransport(private var name: String) : Transport() {
	override fun getTheName(): String {
		return name
	}
}