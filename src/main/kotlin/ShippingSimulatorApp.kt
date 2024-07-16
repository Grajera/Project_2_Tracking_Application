package com.example.shippingsimulator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun shippingSimulatorApp() {
    val shipmentTracker = ShipmentTracker.instance
    var trackingNumber by remember { mutableStateOf("") }
    var shipments = remember { mutableStateListOf<Shipment>() }

    LaunchedEffect(Unit) {
        shipmentTracker.addShipmentUpdateListener { updatedShipment ->
            shipments.clear()
            shipments.addAll(updatedShipment) // Refresh the shipment list
        }

        while (true) {
            delay(1000)
            try {
                val fileContent = withContext(Dispatchers.IO) {
                    File("src/test.txt").readText()
                }
                shipmentTracker.processUpdates(fileContent)
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
                        shipmentCard(shipment, shipmentTracker::stopTracking)
                    }
                }
            }
        }
    }
}

@Composable
fun shipmentCard(shipment: Shipment, onRemoveShipment: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp, // Increased elevation for a shadow effect
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Shipment ID: ${shipment.id}", style = MaterialTheme.typography.h6)
                Button(
                    onClick = { onRemoveShipment(shipment.id) },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text("Remove")
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Spacer for spacing between text and icon
            Text("Status: ${shipment.status}", style = MaterialTheme.typography.body1)
            Text("Location: ${shipment.location}", style = MaterialTheme.typography.body1)
            Text("Expected Delivery: ${shipment.getFormattedExpectedDeliveryDate()}", style = MaterialTheme.typography.body1)
            Text("Notes: ${shipment.notes.joinToString(", ")}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp)) // Spacer for updates section
            Text("Updates:", style = MaterialTheme.typography.h6)
            shipment.updates.forEach { update ->
                Text("  - ${update.updateType} on ${formatTimestamp(update.timestamp)}", style = MaterialTheme.typography.body2)
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("EEE, MMM dd, yyyy HH:mm:ss a", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}



