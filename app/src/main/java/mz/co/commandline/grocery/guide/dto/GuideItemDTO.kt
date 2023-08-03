package mz.co.commandline.grocery.guide.dto

import mz.co.commandline.grocery.rent.dto.RentItemDTO
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import java.math.BigDecimal

class GuideItemDTO() {

    var rentItemDTO: RentItemDTO? = null

    var saleItemDTO: SaleItemDTO? = null

    var quantity: BigDecimal? = null

    var name: String? = null

    constructor(rentItemDTO: RentItemDTO, quantity: BigDecimal) : this() {
        this.rentItemDTO = rentItemDTO
        this.quantity = quantity
    }

    constructor(saleItemDTO: SaleItemDTO, quantity: BigDecimal) : this() {
        this.saleItemDTO = saleItemDTO
        this.quantity = quantity
    }
}
