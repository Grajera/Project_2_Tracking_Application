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

        assertEquals("shipped", shipment.status)
        assertEquals("1672531199000", shipment.expectedDeliveryDate.toString())
        assertEquals(1, shipment.updates.size)
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
        println(shipment.notes)

        assertEquals(1, shipment.notes.size)
        assertTrue(shipment.notes.contains("Fragile"))
    }
}