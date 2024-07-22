package com.example.shippingsimulator

import ShippingStrategies.*
import java.text.SimpleDateFormat
import java.util.*

data class Shipment(
     private val _id: String,
     private var _status: String = "created",
     private var _location: String = "Unknown",
     private var _expectedDeliveryDate: Long = 0,
     private val _notes: MutableList<String> = mutableListOf(),
     private val _updates: MutableList<ShipmentUpdate> = mutableListOf(),
     private val shipmentObserver: ShipmentObserver = ShipmentObserver()
) {
    private val updateStrategies: Map<String, ShipmentUpdateStrategy> = mapOf(
    "created" to CreatedUpdateStrategy(),
    "shipped" to ShippedUpdateStrategy(),
    "location" to LocationUpdateStrategy(),
    "delivered" to DeliveredUpdateStrategy(),
    "delayed" to DelayedUpdateStrategy(),
    "lost" to LostUpdateStrategy(),
    "canceled" to CanceledUpdateStrategy(),
    "noteadded" to NoteAddedUpdateStrategy()
)

    fun getUpdates(): List<ShipmentUpdate> = _updates

    fun getNotes(): List<String> = _notes

    fun getLocation(): String = _location

    fun getExpectedDeliveryDate(): Long {
        return _expectedDeliveryDate
    }

    fun getStatus(): String = _status

    fun getId(): String = _id

    fun addUpdate(update: ShipmentUpdate) {
        _updates.add(update)
        updateStrategies[update.updateType]?.applyUpdate(this, update)
        notifyObservers()
    }

    fun getFormattedExpectedDeliveryDate(): String {
        return java.text.SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(java.util.Date(_expectedDeliveryDate))
    }

    fun getShipmentStatus(): String {
        return "Shipment ID: $_id, Status: $_status, Location: $_location"
    }

    fun setStatus(newStatus: String) {
        _status = newStatus
    }

    fun setLocation(newLocation: String) {
        _location = newLocation
    }

    fun setExpectedDeliveryDate(newDate: Long) {
        _expectedDeliveryDate = newDate
    }

    fun addNote(note: String) {
        _notes.add(note)
    }

    fun addShipmentUpdateListener(listener: (List<Shipment>) -> Unit) {
        shipmentObserver.addListener(listener)
    }

    private fun notifyObservers() {
        shipmentObserver.notifyAllListeners(listOf(this))
    }
}

