package mz.co.commandline.grocery.rent.dto

import mz.co.commandline.grocery.generics.dto.GenericDTO
import java.math.BigDecimal

class RentPaymentDTO(var paymentDate: String, var paymentValue: BigDecimal, private var rentDTO: RentDTO) : GenericDTO()