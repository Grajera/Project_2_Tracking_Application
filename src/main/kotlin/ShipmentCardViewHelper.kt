import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.shippingsimulator.Shipment
import java.text.SimpleDateFormat
import java.util.*

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
                Text("Shipment ID: ${shipment.getId()}", style = MaterialTheme.typography.h6)
                Button(
                    onClick = { onRemoveShipment(shipment.getId()) },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text("Remove")
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Spacer for spacing between text and icon
            Text("Status: ${shipment.getStatus()}", style = MaterialTheme.typography.body1)
            Text("Location: ${shipment.getLocation()}", style = MaterialTheme.typography.body1)
            Text("Expected Delivery: ${shipment.getFormattedExpectedDeliveryDate()}", style = MaterialTheme.typography.body1)
            Text("Notes:", style = MaterialTheme.typography.h6)
            Column(modifier = Modifier.padding(start = 16.dp)) {
                shipment.getNotes().forEach { note ->
                    Text(
                        text = "- $note",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Spacer for updates section
            Text("Updates:", style = MaterialTheme.typography.h6)
            shipment.getUpdates().forEach { update ->
                Text("  - ${update.updateType} on ${formatTimestamp(update.timestamp)}", style = MaterialTheme.typography.body2)
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("EEE, MMM dd, yyyy HH:mm:ss a", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}



