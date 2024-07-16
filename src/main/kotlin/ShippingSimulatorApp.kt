package com.example.shippingsimulator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    var shipments = remember { mutableStateListOf<Shipment>() }

    LaunchedEffect(Unit) {
        // Register the listener to update shipments on changes
        shipmentTracker.addShipmentUpdateListener { updatedShipments ->
            shipments.clear()
            shipments.addAll(updatedShipments)
        }

        while (true) {
            delay(1000)
            try {
                val fileContent = withContext(Dispatchers.IO) {
                    File("src/test.txt").readText()
                }
                shipmentTracker.processUpdates(fileContent) // Update tracked shipments
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
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Tracked Shipments:")
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shipments) { shipment ->
                    key(shipment.id) {
                        shipmentCard(shipment)
                    }
                }
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
        elevation = 4.dp
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
