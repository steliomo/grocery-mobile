package mz.co.commandline.grocery.rent.dto

import mz.co.commandline.grocery.generics.dto.GenericDTO

class GuideDTO(private var rentDTO: RentDTO, private var type: String) : GenericDTO() {

    var guideItemsDTO = mutableListOf<GuideItemDTO>()

    var fileName: String? = null

    fun addGuideItemDTO(guideItemDTO: GuideItemDTO) {
        guideItemsDTO.add(guideItemDTO)
    }
}