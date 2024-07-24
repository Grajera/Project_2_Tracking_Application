import com.example.shippingsimulator.*
import kotlinx.coroutines.runBlocking
import kotlin.test.*
import kotlin.test.assertFailsWith

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
    fun testTrackShipment_NonExistingShipment() {
        tracker.trackShipment("INVALID_ID") // Assuming this does not exist
        assertFalse(tracker.doesShipmentExist("INVALID_ID"))
    }

    @Test
    fun testStopTracking_Success() {
        val shipmentId = "12345"
        val mockShipment = "create,12345,0,standard"
        runBlocking {
        tracker.processUpdates(mockShipment)
        }
        tracker.trackShipment(shipmentId)
        assertTrue(tracker.doesShipmentExist(shipmentId))

        tracker.stopTracking(shipmentId)
        assertNull(tracker.getShipments().find { it.getId() == shipmentId })
    }

    @Test
    fun testProcessUpdates() {

        val mockFileContent = "created,12345,1652712855468,standard\ncreated,123456,1652712855468,standard\n"

        runBlocking {
            tracker.processUpdates(mockFileContent)
            tracker.trackShipment("12345")
            tracker.trackShipment("123456")
        }
        val mockUpdateFileContent = "shipped,12345,1652712855468\ndelivered,123456,1652712855468\n"
        runBlocking {
            tracker.processUpdates(mockUpdateFileContent)
        }
        val shipments = tracker.getShipments()
        assertEquals("shipped", shipments.find { it.getId() == "12345" }?.getStatus())
        assertEquals("delivered", shipments.find { it.getId() == "123456" }?.getStatus())
    }

    @Test
    fun testInvalidShipmentType() {
        assertFailsWith<IllegalArgumentException>{tracker.trackShipment("12345")
            tracker.trackShipment("123456")

            val mockFileContent = "shipped,12345,1652712855468,1652713940874\ndelivered,123456,1652712855468\n"
            runBlocking {
                tracker.processUpdates(mockFileContent)
            }}
    }

    @Test
    fun shipmentCard_DisplaysCorrectData() {
        val shipment = Shipment("12345", "created", "Unknown", 0)
        val formattedDate = shipment.getFormattedExpectedDeliveryDate()
        assertEquals("Wed, Dec 31, 1969 17:00:00 PM", formattedDate) // Update based on your formatting
    }
}
