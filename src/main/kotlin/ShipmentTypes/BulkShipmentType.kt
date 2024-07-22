package ShipmentTypes

class BulkShipmentType : ShipmentType {
    private val minDaysAfterCreation = 3

    override fun validateDeliveryDate(expectedDeliveryDate: Long, creationDate: Long): ValidationResult {
        val minDate = creationDate + (minDaysAfterCreation * 24 * 60 * 60 * 1000)
        return if (expectedDeliveryDate < minDate) {
            ValidationResult(false, ErrorCodes.BULK_DELIVERY_TOO_SOON)
        } else {
            ValidationResult(true)
        }
    }

    override fun getAbnormalMessage(errorCode: String): String {
        return when (errorCode) {
            ErrorCodes.BULK_DELIVERY_TOO_SOON -> "A bulk shipment should not have an expected delivery date sooner than 3 days after it was created."
            else -> ""
        }
    }
}