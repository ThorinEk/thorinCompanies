package dev.gustavpersson.thorincompanies.business_logic_layer.exceptions

import dev.gustavpersson.thorincompanies.business_logic_layer.enums.ErrorCode

class ThorinException(code: ErrorCode, message: String? = null) : Exception(message)
