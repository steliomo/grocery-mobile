package mz.co.commandline.grocery.pos.dto

import mz.co.commandline.grocery.customer.model.CustomerDTO
import java.math.BigDecimal

class DebtDTO {
    var customer: CustomerDTO? = null
    var totalToPay: BigDecimal? = null
    var totalPaid: BigDecimal? = null
    var totalInDebt: BigDecimal? = null
    var amount: BigDecimal? = null

    val debtItems = mutableListOf<DebtItemDTO>()
}