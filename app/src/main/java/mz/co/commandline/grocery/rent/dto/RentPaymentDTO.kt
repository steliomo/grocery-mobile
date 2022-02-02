package mz.co.commandline.grocery.rent.dto

import mz.co.commandline.grocery.generics.dto.GenericDTO
import java.math.BigDecimal

class RentPaymentDTO(private var paymentDate: String, private var paymentValue: BigDecimal, private var rentDTO: RentDTO) : GenericDTO()