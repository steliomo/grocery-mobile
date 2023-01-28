package mz.co.commandline.grocery.rent.dto

import java.math.BigDecimal

class GuideItemDTO(private var rentItemDTO: RentItemDTO, private var quantity: BigDecimal) {}
