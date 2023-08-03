package mz.co.commandline.grocery.guide.dto

import mz.co.commandline.grocery.generics.dto.GenericDTO
import mz.co.commandline.grocery.rent.dto.RentDTO
import mz.co.commandline.grocery.sale.dto.SaleDTO

class GuideDTO() : GenericDTO() {

    var guideItemsDTO = mutableListOf<GuideItemDTO>()

    var filePath: String? = null

    var issueDate: String? = null

    var code: String? = null

    var saleDTO: SaleDTO? = null

    var type: GuideType? = null

    var rentDTO: RentDTO? = null

    constructor(rentDTO: RentDTO, type: GuideType) : this() {
        this.rentDTO = rentDTO
        this.type = type
    }

    constructor(saleDTO: SaleDTO, type: GuideType) : this() {
        this.saleDTO = saleDTO
        this.type = type
    }

    fun addGuideItemDTO(guideItemDTO: GuideItemDTO) {
        guideItemsDTO.add(guideItemDTO)
    }
}