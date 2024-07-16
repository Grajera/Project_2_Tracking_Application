package com.example.shippingsimulator

import java.text.SimpleDateFormat
import java.util.*

data class Shipment(
    private val id: String,
    private var status: String = "created",
    private var location: String = "Unknown",
    private var expectedDeliveryDate: Long = 0,
    private val notes: MutableList<String> = mutableListOf(),
    private val updates: MutableList<ShipmentUpdate> = mutableListOf()
) {
    fun addUpdate(update: ShipmentUpdate) {
        updates.add(update)
        when (update.updateType) {
            "created" -> status = "created"
            "shipped" -> {
                status = "shipped"
                expectedDeliveryDate = update.otherInfo?.toLong() ?: expectedDeliveryDate
            }
            "location" -> location = update.otherInfo ?: location
            "delivered" -> status = "delivered"
            "delayed" -> expectedDeliveryDate = update.otherInfo?.toLong() ?: expectedDeliveryDate
            "lost" -> status = "lost"
            "canceled" -> status = "canceled"
            "noteadded" -> notes.add(update.otherInfo ?: "")
        }
    }

    fun getFormattedExpectedDeliveryDate(): String {
        val date = Date(expectedDeliveryDate)
        val format = SimpleDateFormat("EEE, MMM dd, yyyy HH:mm:ss a", Locale.getDefault())
        return format.format(date)
    }

    fun getUpdates(): List<ShipmentUpdate> = updates

    fun getNotes(): List<String> = notes

    fun getLocation(): String = location

    fun getStatus(): String = status

    fun getId(): String = id
}

