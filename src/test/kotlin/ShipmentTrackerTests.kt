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
        assertTrue(tracker.doesShipmentExist(shipmentId))
    }

    @Test
    fun testStopTracking() {
        val shipmentId = "12345"
        tracker.trackShipment(shipmentId)
        assertTrue(tracker.doesShipmentExist(shipmentId))
        tracker.stopTracking(shipmentId)
        assertFalse(tracker.getShipments().any { it.getId() == shipmentId })
    }

    @Test
    fun testTrackShipment_ExistingShipment() {
        tracker.trackShipment("12345") // Assuming SH001 exists in the source
        assertTrue(tracker.doesShipmentExist("12345"))
        assertNotNull(tracker.getShipments().find { it.getId() == "12345" })
    }

    @Test
    fun testTrackShipment_NonExistingShipment() {
        tracker.trackShipment("INVALID_ID") // Assuming this does not exist
        assertFalse(tracker.doesShipmentExist("INVALID_ID"))
    }

    @Test
    fun testStopTracking_Success() {
        val shipmentId = "12345"
        tracker.trackShipment(shipmentId) // Track it first
        assertTrue(tracker.doesShipmentExist(shipmentId))

        tracker.stopTracking(shipmentId)
        assertNull(tracker.getShipments().find { it.getId() == shipmentId })
    }

    @Test
    fun testProcessUpdates() {
        tracker.trackShipment("12345")
        tracker.trackShipment("123456")

        val mockFileContent = "shipped,12345,1652712855468,1652713940874\ndelivered,123456,1652712855468\n"
        runBlocking {
            tracker.processUpdates(mockFileContent)
        }

        val shipments = tracker.getShipments()
        assertEquals("shipped", shipments.find { it.getId() == "12345" }?.getStatus())
        assertEquals("delivered", shipments.find { it.getId() == "123456" }?.getStatus())
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

    @Test
    fun shipmentCard_DisplaysCorrectData() {
        val shipment = Shipment("12345", "created", "Unknown", 0)
        val formattedDate = shipment.getFormattedExpectedDeliveryDate()
        assertEquals("Wed, Dec 31, 1969 17:00:00 PM", formattedDate) // Update based on your formatting
    }
}
