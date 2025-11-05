package mz.co.commandline.grocery.pos.dto

import java.math.BigDecimal

class DebtItemDTO {
    var debtDate: String? = null
    var name: String? = null
    var quantity: BigDecimal? = null
    var price: BigDecimal? = null
    var debtItemValue: BigDecimal? = null
}