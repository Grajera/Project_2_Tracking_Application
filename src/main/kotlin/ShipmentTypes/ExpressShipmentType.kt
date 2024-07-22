package ShipmentTypes

class ExpressShipmentType : ShipmentType {
    private val maxDaysAfterCreation = 3

    override fun validateDeliveryDate(expectedDeliveryDate: Long, creationDate: Long): ValidationResult {
        val maxDate = creationDate + (maxDaysAfterCreation * 24 * 60 * 60 * 1000)
        return if (expectedDeliveryDate > maxDate) {
            ValidationResult(false, ErrorCodes.EXPRESS_DELIVERY_TOO_LATE)
        } else {
            ValidationResult(true)
        }
    }

    override fun getAbnormalMessage(errorCode: String): String {
        return when (errorCode) {
            ErrorCodes.EXPRESS_DELIVERY_TOO_LATE -> "An express shipment cannot have an expected delivery date of more than 3 days after the shipment was created."
            else -> ""
        }
    }
}