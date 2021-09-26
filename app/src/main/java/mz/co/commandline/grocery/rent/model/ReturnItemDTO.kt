package mz.co.commandline.grocery.rent.model

import java.math.BigDecimal

class ReturnItemDTO(var rentItemDTO: RentItemDTO, var quantity: BigDecimal, var returnDate: String)