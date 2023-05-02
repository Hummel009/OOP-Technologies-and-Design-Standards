package hummel

open class CarTransport(private var name: String) : Transport() {
	override fun getTheName(): String {
		return name
	}
}