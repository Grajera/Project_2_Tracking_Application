import com.example.shippingsimulator.*
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class IntegrationTests {

    private val tracker = ShipmentTracker.instance

    @Test
    fun testFullCycle() {
        val shipmentId = "12345"
        tracker.trackShipment(shipmentId)

        val update = "shipped,12345,1672531199000\n"
        runBlocking {
            tracker.processUpdates(update)
        }

        val shipment = tracker.getShipments().find { it.getId() == shipmentId }
        assertNotNull(shipment)
        assertEquals("shipped", shipment.getStatus())

        tracker.stopTracking(shipmentId)
        assertFalse(tracker.getShipments().any { it.getId() == shipmentId })
    }
}
