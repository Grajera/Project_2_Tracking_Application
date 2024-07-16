package com.example.shippingsimulator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import shipmentCard
import java.io.File

@Composable
fun shippingSimulatorApp() {
    val shipmentTracker = ShipmentTracker.instance
    var trackingNumber by remember { mutableStateOf("") }
    val shipments = remember { mutableStateListOf<Shipment>() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        shipmentTracker.addShipmentUpdateListener { updatedShipments ->
            shipments.clear()
            shipments.addAll(updatedShipments) // Refresh the shipment list
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
                onValueChange = {
                    trackingNumber = it
                    errorMessage = null // Clear error message when tracking number changes
                },
                label = { Text("Enter Tracking Number") }
            )
            Row {
                Button(onClick = {
                    // Check if the shipment exists before tracking
                    val shipmentExists = shipmentTracker.doesShipmentExist(trackingNumber)
                    if (shipmentExists) {
                        shipmentTracker.trackShipment(trackingNumber)
                    } else {
                        errorMessage = "Shipment with ID $trackingNumber does not exist."
                    }
                }) {
                    Text("Track Shipment")
                }
            }
            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Tracked Shipments:")
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shipments) { shipment ->
                    key(shipment.getId()) {
                        shipmentCard(shipment, shipmentTracker::stopTracking)
                    }
                }
            }
        }
    }
}