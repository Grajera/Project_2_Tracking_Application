package com.example.shippingsimulator

data class Shipment(
    val id: String,
    var status: String = "created",
    var location: String = "Unknown",
    var expectedDeliveryDate: Long = 0,
    val notes: MutableList<String> = mutableListOf(),
    val updates: MutableList<ShipmentUpdate> = mutableListOf()
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
        return expectedDeliveryDate.toString() // You can format this as needed
    }
}
