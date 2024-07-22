package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

class NoteAddedUpdateStrategy : ShipmentUpdateStrategy {
    override fun applyUpdate(shipment: Shipment, update: ShipmentUpdate) {
        shipment.addNote(update.otherInfo ?: "")
    }
}
