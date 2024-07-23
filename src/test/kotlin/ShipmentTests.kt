import com.example.shippingsimulator.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class ShipmentTests {

    @Test
    fun addUpdate_CreatesShipment() {
        val shipment = Shipment("SH001")
        shipment.addUpdate(ShipmentUpdate("created", "12345", 1630000000))
        assertEquals("created", shipment.getStatus())
    }

    @Test
    fun addUpdate_UpdatesStatus() {
        val shipment = Shipment("SH002")
        shipment.addUpdate(ShipmentUpdate( "shipped", "12345", 1630000000, "1630500000"))
        assertEquals("shipped", shipment.getStatus())
        assertEquals("Mon, Jan 19, 1970 13:55:00 PM", shipment.getFormattedExpectedDeliveryDate())
    }

    @Test
    fun addUpdate_AddsNote() {
        val shipment = Shipment("12345")
        shipment.addUpdate(ShipmentUpdate( "noteadded", "12345", 1630000000, "Fragile"))
        assertEquals(listOf("Fragile"), shipment.getNotes())
    }

    @Test
    fun getFormattedExpectedDeliveryDate_ReturnsFormattedDate() {
        val shipment = Shipment("12345", expectedDeliveryDate = 1630000000)
        val formattedDate = shipment.getFormattedExpectedDeliveryDate()
        assertEquals("Mon, Jan 19, 1970 13:46:40 PM", formattedDate)
    }

    @Test
    fun getLocation_ReturnsGetLocation() {
        val shipment = Shipment("12345", expectedDeliveryDate = 1630000000)
        shipment.addUpdate(ShipmentUpdate( "location", "12345", 1630000000, "Logan, Utah"))
        assertEquals("Logan, Utah", shipment.getLocation())
    }

    @Test
    fun getId_ReturnsGetId() {
        val shipment = Shipment("12345", expectedDeliveryDate = 16332132151)
        assertEquals("12345", shipment.getId())
    }

    @Test
    fun getUpdates_ReturnsGetUpdates() {
        // Arrange
        val shipment = Shipment(id = "12345")
        val update1 = ShipmentUpdate("12345", "shipped", System.currentTimeMillis())
        val update2 = ShipmentUpdate("12345", "delivered", System.currentTimeMillis())

        shipment.addUpdate(update1)
        shipment.addUpdate(update2)

        // Act
        val updates = shipment.getUpdates()

        // Assert
        assertEquals(2, updates.size)
        assertEquals(update1, updates[0])
        assertEquals(update2, updates[1])
    }
}