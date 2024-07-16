package com.example.shippingsimulator

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import java.io.File

@Composable
fun shippingSimulatorApp() {
    val shipmentTracker = ShipmentTracker.instance
    var trackingNumber by remember { mutableStateOf("") }
    var shipments by remember { mutableStateOf(emptyList<Shipment>()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            try {
                // Read the file contents
                val fileContent = withContext(Dispatchers.IO) {
                    File("src/test.txt").readText()
                }
                // Process updates for shipments
                shipmentTracker.processUpdates(fileContent)

                // Refresh the list of shipments
                shipments = shipmentTracker.getShipments()
            } catch (e: Exception) {
                println("Error reading file: ${e.message}")
            }
        }
    }

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = trackingNumber,
                onValueChange = { trackingNumber = it },
                label = { Text("Enter Tracking Number") }
            )
            Row {
                Button(onClick = {
                    shipmentTracker.trackShipment(trackingNumber)
                }) {
                    Text("Track Shipment")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    shipmentTracker.stopTracking(trackingNumber)
                }) {
                    Text("Remove Shipment")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    shipmentTracker.printShipmentStatus(trackingNumber)
                }) {
                    Text("Print Shipment Status")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Tracked Shipments:")
            shipments.forEach { shipment ->
                shipmentCard(shipment)
            }
        }
    }
}

@Composable
fun shipmentCard(shipment: Shipment) {
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
