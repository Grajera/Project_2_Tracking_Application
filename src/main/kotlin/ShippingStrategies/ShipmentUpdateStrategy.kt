package ShippingStrategies

import com.example.shippingsimulator.Shipment
import com.example.shippingsimulator.ShipmentUpdate

interface ShipmentUpdateStrategy {
    fun applyUpdate(shipment: Shipment, update: ShipmentUpdate)
}