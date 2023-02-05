package mz.co.commandline.grocery.rent.dto

import mz.co.commandline.grocery.generics.dto.GenericDTO

class GuideDTO(var rentDTO: RentDTO, var type: GuideType) : GenericDTO() {

    var guideItemsDTO = mutableListOf<GuideItemDTO>()

    var fileName: String? = null

    var issueDate: String? = null

    var code: String? = null

    fun addGuideItemDTO(guideItemDTO: GuideItemDTO) {
        guideItemsDTO.add(guideItemDTO)
    }
}