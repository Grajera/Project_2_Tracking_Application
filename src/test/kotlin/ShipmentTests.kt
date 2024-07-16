import com.example.shippingsimulator.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class ShipmentTests {

    @Test
    fun testAddUpdate() {
        val shipment = Shipment(id = "12345")
        val update = ShipmentUpdate(
            shipmentId = "12345",
            updateType = "shipped",
            timestamp = System.currentTimeMillis(),
            otherInfo = "1672531199000" // Example delivery date
        )

        shipment.addUpdate(update)

        assertEquals("shipped", shipment.getStatus())
        assertEquals(1, shipment.getUpdates().size)
    }

    @Test
    fun testGetFormattedExpectedDeliveryDate() {
        val shipment = Shipment(id = "12345", expectedDeliveryDate = 1672531199000L)
        val expectedDate = "Sat, Dec 31, 2022 16:59:59 PM"

        assertEquals(expectedDate, shipment.getFormattedExpectedDeliveryDate())
    }

    @Test
    fun testAddNotes() {
        val shipment = Shipment(id = "12345")
        shipment.addUpdate(ShipmentUpdate("noteadded", "12345", System.currentTimeMillis(), "Fragile"))
        println(shipment.getNotes())

        assertEquals(1, shipment.getNotes().size)
        assertTrue(shipment.getNotes().contains("Fragile"))
    }
}