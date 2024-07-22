package ShipmentTypes

class StandardShipmentType : ShipmentType {
    override fun validateDeliveryDate(expectedDeliveryDate: Long, creationDate: Long): ValidationResult {
        return ValidationResult(true) // No special conditions
    }

    override fun getAbnormalMessage(errorCode: String): String {
        return "" // No error messages for StandardShipmentType
    }
}
