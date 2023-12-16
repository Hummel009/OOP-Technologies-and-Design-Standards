package hummel.transport

import hummel.special.*

abstract class CarBasic(var price: Int = 0, var color: String = "Color", name: String = "Name") : Component(name),
	Transport {
	private val listeners: MutableList<Listener> = mutableListOf()
	var speed: Int = 0
		set(value) {
			field = value
			notifySpeedChangeListeners()
		}
	private val id = ++count
	private var engine: Engine? = null
	private val components = mutableListOf<Component>()

	override fun display() {
		println("Car: $name")
		for (component in components) {
			component.display()
		}
	}

	override fun getTheInfo(): String = "$name ($color): $price$"

	override fun toString(): String = "Car $id"

	fun add(component: Component): Boolean = components.add(component)

	fun remove(component: Component): Boolean = components.remove(component)

	fun addSpeedChangeListener(listener: Listener): Boolean = listeners.add(listener)

	fun removeSpeedChangeListener(listener: Listener): Boolean = listeners.remove(listener)

	fun setEngine(engine: Engine) {
		this.engine = engine
	}

	fun getEngine(): String = engine?.getNewDesc() ?: "Benz"

	private fun notifySpeedChangeListeners() {
		for (listener in listeners) {
			listener.onSpeedChange(this, speed)
		}
	}

	abstract fun accept(visitor: Visitor)

	companion object {
		private var count = 0
	}

	init {
		println("Creating car $id")
	}
}