import ShipmentTypes.BulkShipmentType
import ShipmentTypes.ExpressShipmentType
import ShipmentTypes.OvernightShipmentType
import ShipmentTypes.StandardShipmentType
import com.example.shippingsimulator.*
import kotlinx.coroutines.runBlocking
import kotlin.test.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipmentTypesTests {

    @Test
    fun testBulkShipmentType()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = BulkShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", System.currentTimeMillis())
            shipment.addUpdate(update1)
            assertTrue(shipment.getNotes().isEmpty())
            val update2 = ShipmentUpdate("12345", "delivered", System.currentTimeMillis())
            shipment.addUpdate(update2)
        }
    }

    @Test
    fun testBulkShipmentTypeShippingTimeTooSoon()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = BulkShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", 0)
            shipment.addUpdate(update1)
            assertEquals(shipment.getNotes()[0], "A bulk shipment should not have an expected delivery date sooner than 3 days after it was created.")
            assertEquals(shipment.getStatus(), "abnormal")
        }
    }

    @Test
    fun testExpressShipmentType()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = ExpressShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", 0)
            shipment.addUpdate(update1)
            assertTrue(shipment.getNotes().isEmpty())
            val update2 = ShipmentUpdate("12345", "delivered", System.currentTimeMillis())
            shipment.addUpdate(update2)
        }
    }

    @Test
    fun testExpressShipmentTypeShippingTimeTooLate()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = ExpressShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", 99999999999)
            shipment.addUpdate(update1)
            assertEquals(shipment.getNotes()[0], "An express shipment cannot have an expected delivery date of more than 3 days after the shipment was created.")
            assertEquals(shipment.getStatus(), "abnormal")
        }
    }

    @Test
    fun testOvernightShipmentType()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = OvernightShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", 86400000)
            shipment.addUpdate(update1)
            assertTrue(shipment.getNotes().isEmpty())
            val update2 = ShipmentUpdate("12345", "delivered", 86400000)
            shipment.addUpdate(update2)
        }
    }

    @Test
    fun testOvernightShipmentTypeShippingTimeTooLate()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = OvernightShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", 99999999999)
            shipment.addUpdate(update1)
            assertEquals(shipment.getNotes()[0], "An overnight shipment must have an expected delivery date of the day after it was created.")
            assertEquals(shipment.getStatus(), "abnormal")
        }
    }

    @Test
    fun testOvernightShipmentTypeShippingTimeTooSoon()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = OvernightShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", 0)
            shipment.addUpdate(update1)
            assertEquals(shipment.getNotes()[0], "An overnight shipment must have an expected delivery date of the day after it was created.")
            assertEquals(shipment.getStatus(), "abnormal")
        }
    }

    @Test
    fun testStandardShipmentType()
    {
        runBlocking {
            val shipmentId = "12345"
            val shipment = Shipment(shipmentId, expectedDeliveryDate = 0, shipmentType = StandardShipmentType())
            val update1 = ShipmentUpdate("12345", "shipped", System.currentTimeMillis())
            shipment.addUpdate(update1)
            assertTrue(shipment.getNotes().isEmpty())
            val update2 = ShipmentUpdate("12345", "delivered", System.currentTimeMillis())
            shipment.addUpdate(update2)
        }
    }
}