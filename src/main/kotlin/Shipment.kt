package com.example.shippingsimulator

import ShipmentTypes.*
import ShippingStrategies.*
import java.text.SimpleDateFormat
import java.util.*

data class Shipment(
    private val id: String,
    private var status: String = "created",
    private var location: String = "Unknown",
    private var expectedDeliveryDate: Long = 0,
    private val notes: MutableList<String> = mutableListOf(),
    private val updates: MutableList<ShipmentUpdate> = mutableListOf(),
    private val shipmentObserver: ShipmentObserver = ShipmentObserver(),
    private val shipmentType: ShipmentType = StandardShipmentType() // Default type
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


    fun getUpdates(): List<ShipmentUpdate> = updates

    fun getNotes(): List<String> = notes

    fun getLocation(): String = location

    fun getExpectedDeliveryDate(): Long {
        return expectedDeliveryDate
    }

    fun getStatus(): String = status

    fun getId(): String = id

    fun addUpdate(update: ShipmentUpdate) {
        updates.add(update)
        updateStrategies[update.updateType]?.applyUpdate(this, update)
        validateShipment()
        notifyObservers()
    }

    fun getFormattedExpectedDeliveryDate(): String {
        return SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(Date(expectedDeliveryDate))
    }

    fun setStatus(newStatus: String) {
        status = newStatus
    }

    fun setLocation(newLocation: String) {
        location = newLocation
    }

    fun setExpectedDeliveryDate(newDate: Long) {
        expectedDeliveryDate = newDate
    }

    fun addNote(note: String) {
        notes.add(note)
    }

    private fun validateShipment() {
        val validationResult = shipmentType.validateDeliveryDate(updates.last().timestamp, expectedDeliveryDate)
        if (!validationResult.isValid) {
            status = "abnormal"
            notifyObservers(validationResult.errorCode)
        }
        else {
            notifyObservers()
        }
    }

    private fun notifyObservers(errorCode: String? = null) {
        val message = errorCode?.let { shipmentType.getAbnormalMessage(it) } ?: ""
        if (message != "") {
            notes.add(message)
        }
        shipmentObserver.notifyAllListeners(listOf(this), message)
    }
}

