package com.example.shippingsimulator

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShippingSimulatorApp() {
    val shipmentTracker = ShipmentTracker.instance
    var trackingId by remember { mutableStateOf("") }
    val shipments = shipmentTracker.getShipments()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Shipping Tracker")

        OutlinedTextField(
            value = trackingId,
            onValueChange = { trackingId = it },
            label = { Text("Enter Tracking ID") }
        )

        Button(onClick = {
            shipmentTracker.trackShipment(trackingId)
        }) {
            Text("Track")
        }

        Button(onClick = {
            shipmentTracker.stopTracking(trackingId)
        }) {
            Text("Stop Tracking")
        }

        shipments.forEach { shipment ->
            ShipmentCard(shipment)
        }
    }
}

@Composable
fun ShipmentCard(shipment: Shipment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp // Use a fixed elevation instead of CardDefaults
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Shipment ID: ${shipment.id}")
            Text("Status: ${shipment.status}")
            Text("Location: ${shipment.location}")
            Text("Expected Delivery: ${shipment.getFormattedExpectedDeliveryDate()}")
            Text("Notes: ${shipment.notes.joinToString(", ")}")
            Text("Updates:")
            shipment.updates.forEach { update ->
                Text("  - ${update.updateType} on ${update.timestamp}")
            }
        }
    }
}
