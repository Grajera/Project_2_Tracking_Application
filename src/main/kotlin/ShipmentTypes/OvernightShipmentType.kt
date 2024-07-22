package ShipmentTypes

class OvernightShipmentType : ShipmentType {
    private val oneDayInMillis = 24 * 60 * 60 * 1000

    override fun validateDeliveryDate(expectedDeliveryDate: Long, creationDate: Long): ValidationResult {
        val expectedDate = creationDate + oneDayInMillis
        return if (expectedDeliveryDate != expectedDate) {
            ValidationResult(false, ErrorCodes.OVERNIGHT_DELIVERY_INVALID)
        } else {
            ValidationResult(true)
        }
    }

    override fun getAbnormalMessage(errorCode: String): String {
        return when (errorCode) {
            ErrorCodes.OVERNIGHT_DELIVERY_INVALID -> "An overnight shipment must have an expected delivery date of the day after it was created."
            else -> ""
        }
    }
}