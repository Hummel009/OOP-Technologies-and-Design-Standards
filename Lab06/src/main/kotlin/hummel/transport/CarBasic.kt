package hummel.transport

import hummel.special.Component
import hummel.special.Engine
import hummel.special.Listener
import hummel.special.Transport
import hummel.special.Visitor

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

	override fun getTheInfo(): String {
		return "$name ($color): $price$"
	}

	override fun toString(): String {
		return "Car $id"
	}

	fun add(component: Component) {
		components.add(component)
	}

	fun remove(component: Component) {
		components.remove(component)
	}

	fun addSpeedChangeListener(listener: Listener) {
		listeners.add(listener)
	}

	fun removeSpeedChangeListener(listener: Listener) {
		listeners.remove(listener)
	}

	fun setEngine(engine: Engine) {
		this.engine = engine
	}

	fun getEngine(): String {
		return engine?.getNewDesc() ?: "Benz"
	}

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