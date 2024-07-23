import com.example.shippingsimulator.*
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class FullCycleTest {

    private val tracker = ShipmentTracker.instance

    @Test
    fun testFullCycle() {
        val shipmentId = "12345"
        var update = "created,12345,1672531199000,standard\n"
        runBlocking {
            tracker.processUpdates(update)
            tracker.trackShipment(shipmentId)
            update = "shipped,12345,1672999999000\n"
            tracker.processUpdates(update)
        }

        val shipment = tracker.getShipments().find { it.getId() == shipmentId }
        assertNotNull(shipment)
        assertEquals("shipped", shipment.getStatus())

        tracker.stopTracking(shipmentId)
        assertFalse(tracker.getShipments().any { it.getId() == shipmentId })
    }
}
