import ShipmentTypes.BulkShipmentType
import ShipmentTypes.ExpressShipmentType
import ShipmentTypes.OvernightShipmentType
import ShipmentTypes.StandardShipmentType
import com.example.shippingsimulator.*
import kotlinx.coroutines.runBlocking
import kotlin.test.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ShippingStratigiesTests {

    @Test
    fun testShipmentUpdate()
    {
        val shipmentTime = System.currentTimeMillis()
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        val update = ShipmentUpdate("shipped", "12345", shipmentTime, shipmentTime.toString())
        shipment.addUpdate(update)
        assertEquals(shipment.getStatus(), "shipped")
        assertEquals(shipment.getExpectedDeliveryDate(), shipmentTime)
    }

    @Test
    fun testCanceledShipment()
    {
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        val update1 = ShipmentUpdate("shipped", "12345", System.currentTimeMillis())
        shipment.addUpdate(update1)
        val update2 = ShipmentUpdate( "canceled", "12345", System.currentTimeMillis())
        shipment.addUpdate(update2)
        assertEquals(shipment.getStatus(), "canceled")
    }

    @Test
    fun testCreatedShipment()
    {
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        assertEquals(shipment.getStatus(), "created")
    }

    @Test
    fun testDelayedShipment()
    {
        val newTime = System.currentTimeMillis()
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        val update1 = ShipmentUpdate("shipped", "12345", 0)
        shipment.addUpdate(update1)
        assertEquals(shipment.getStatus(), "shipped")
        val update2 = ShipmentUpdate( "delayed", "12345", newTime, newTime.toString()
        )
        shipment.addUpdate(update2)
        assertEquals(shipment.getStatus(), "delayed")
        assertNotEquals(shipment.getExpectedDeliveryDate(), 0)
    }

    @Test
    fun testLocationShipment()
    {
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        val update1 = ShipmentUpdate("shipped", "12345", System.currentTimeMillis())
        shipment.addUpdate(update1)
        val update2 = ShipmentUpdate( "location", "12345", System.currentTimeMillis(), "Los Angeles CA")
        shipment.addUpdate(update2)
        assertEquals(shipment.getStatus(), "shipped")
        assertEquals(shipment.getLocation(), "Los Angeles CA")
    }

    @Test
    fun testLostShipment()
    {
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        val update1 = ShipmentUpdate("shipped", "12345", System.currentTimeMillis())
        shipment.addUpdate(update1)
        val update2 = ShipmentUpdate( "lost", "12345", System.currentTimeMillis())
        shipment.addUpdate(update2)
        assertEquals(shipment.getStatus(), "lost")
    }

    @Test
    fun testNoteAddedToShipment()
    {
        val shipmentId = "12345"
        val shipment = Shipment(shipmentId, expectedDeliveryDate = 0)
        val update1 = ShipmentUpdate("shipped", "12345", System.currentTimeMillis())
        shipment.addUpdate(update1)
        val update2 = ShipmentUpdate( "noteadded", "12345", System.currentTimeMillis(), "Test Note Added To Shipment")
        shipment.addUpdate(update2)
        assertEquals(shipment.getNotes()[0], "Test Note Added To Shipment")
    }
}