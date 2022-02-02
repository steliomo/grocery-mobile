package mz.co.commandline.grocery.contract.dto

import mz.co.commandline.grocery.customer.model.CustomerDTO
import mz.co.commandline.grocery.generics.dto.EnumDTO
import mz.co.commandline.grocery.generics.dto.GenericDTO
import mz.co.commandline.grocery.grocery.dto.GroceryDTO
import mz.co.commandline.grocery.item.dto.ProductUnitDTO
import java.math.BigDecimal

data class ContractDTO(val unitDTO: GroceryDTO) : GenericDTO() {
    var contractType: EnumDTO? = null
    var description: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var referencePaymentDate:String? = null
    var monthlyPayment: BigDecimal? = null
    var totalPaid:BigDecimal? = null
    var customerDTO: CustomerDTO? = null
}