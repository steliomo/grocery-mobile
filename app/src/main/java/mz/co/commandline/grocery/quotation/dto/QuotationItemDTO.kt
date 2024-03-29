package mz.co.commandline.grocery.quotation.dto

import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO

import mz.co.commandline.grocery.saleable.dto.StockDTO
import java.math.BigDecimal


class QuotationItemDTO(private var quotationDTO: QuotationDTO, var icon: Int) {

    var stockDTO: StockDTO? = null

    var serviceItemDTO: ServiceItemDTO? = null

    var quantity: BigDecimal? = null

    var days: BigDecimal? = null

    fun getName(): String {

        if (serviceItemDTO != null) {
            return serviceItemDTO?.name!!
        }

        return stockDTO?.name!!
    }

    fun getValue(): BigDecimal {

        if (QuotationType.RENT == quotationDTO.type) {
            return calculateValue(days!!)
        }

        return calculateValue(BigDecimal.ONE)
    }

    private fun calculateValue(days: BigDecimal): BigDecimal {
        if (stockDTO == null) {
            return quantity?.multiply(days)!!.multiply(BigDecimal(serviceItemDTO?.rentPrice))
        }
        return quantity?.multiply(days)!!.multiply(BigDecimal(stockDTO?.rentPrice))
    }
}