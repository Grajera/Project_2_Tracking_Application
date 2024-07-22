package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

class DelayedUpdateStrategy : ShipmentUpdateStrategy {
    override fun applyUpdate(shipment: Shipment, update: ShipmentUpdate) {
        shipment.setExpectedDeliveryDate (update.otherInfo?.toLong() ?: shipment.getExpectedDeliveryDate())
    }
}
