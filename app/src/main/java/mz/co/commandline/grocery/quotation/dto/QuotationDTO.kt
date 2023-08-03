package mz.co.commandline.grocery.quotation.dto

import mz.co.commandline.grocery.customer.model.CustomerDTO
import mz.co.commandline.grocery.generics.dto.GenericDTO
import mz.co.commandline.grocery.grocery.dto.UnitDTO
import java.math.BigDecimal


class QuotationDTO(var type: QuotationType) : GenericDTO() {

    var code: String? = null

    var name: String? = null

    var customerDTO: CustomerDTO? = null

    var issueDate: String? = null

    var status: QuotationStatus? = null

    var unitDTO: UnitDTO? = null

    var items = mutableListOf<QuotationItemDTO>()

    var discount: BigDecimal? = BigDecimal.ZERO

    var totalValue: BigDecimal? = BigDecimal.ZERO

    fun addItem(quotationItemDTO: QuotationItemDTO) {
        items.add(quotationItemDTO)
        totalValue = totalValue?.add(quotationItemDTO.getValue())
    }
}