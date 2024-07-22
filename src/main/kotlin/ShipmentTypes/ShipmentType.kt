package ShipmentTypes

interface ShipmentType {
    fun validateDeliveryDate(expectedDeliveryDate: Long, creationDate: Long): ValidationResult
    fun getAbnormalMessage(errorCode: String): String
}

data class ValidationResult(
    val isValid: Boolean,
    val errorCode: String? = null
)