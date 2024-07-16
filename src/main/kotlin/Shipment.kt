package com.example.shippingsimulator

import java.text.SimpleDateFormat
import java.util.*

data class Shipment(
    val id: String,
    var status: String = "created",
    var location: String = "Unknown",
    var expectedDeliveryDate: Long = 0,
    val notes: MutableList<String> = mutableListOf(),
    val updates: MutableList<ShipmentUpdate> = mutableListOf()
) {
    private val dateFormat = SimpleDateFormat("EEE, MMM dd, yyyy HH:mm:ss a")

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
        return dateFormat.format(Date(expectedDeliveryDate)) // Format the expected delivery date
    }
}
