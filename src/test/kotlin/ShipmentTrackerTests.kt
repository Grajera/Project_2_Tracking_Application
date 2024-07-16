import com.example.shippingsimulator.*
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class ShipmentTrackerTests {

    private val tracker = ShipmentTracker.instance

    @Test
    fun testTrackShipment() {
        val shipmentId = "12345"
        tracker.trackShipment(shipmentId)

        val trackedShipments = tracker.getShipments()
        assertTrue(trackedShipments.any { it.getId() == shipmentId })
    }

    @Test
    fun testStopTracking() {
        val shipmentId = "12345"
        tracker.trackShipment(shipmentId)
        tracker.stopTracking(shipmentId)

        val trackedShipments = tracker.getShipments()
        assertFalse(trackedShipments.any { it.getId() == shipmentId })
    }

    @Test
    fun testProcessUpdates() {
        val shipmentId = "12345"
        tracker.trackShipment(shipmentId)

        val update = "shipped,12345,1672531199000\n"
        runBlocking {
            tracker.processUpdates(update)
        }

        val shipment = tracker.getShipments().find { it.getId() == shipmentId }
        println(shipment)
        assertNotNull(shipment)
        assertEquals("shipped", shipment.getStatus())
    }

    @Test
    fun testObserverNotification() {
        var updatedShipments: List<Shipment>? = null
        tracker.addShipmentUpdateListener { shipments ->
            updatedShipments = shipments
        }

        val shipmentId = "12345"
        tracker.trackShipment(shipmentId)

        val update = "shipped,12345,1672531199000\n"
        runBlocking {
            tracker.processUpdates(update)
        }

        assertNotNull(updatedShipments)
        assertTrue(updatedShipments!!.any { it.getId() == shipmentId && it.getStatus() == "shipped" })
    }
}
