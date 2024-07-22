package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

class ShippedUpdateStrategy : ShipmentUpdateStrategy {
    override fun applyUpdate(shipment: Shipment, update: ShipmentUpdate) {
        shipment.setStatus("shipped")
        shipment.setExpectedDeliveryDate(update.otherInfo?.toLong() ?: shipment.getExpectedDeliveryDate())
    }
}
