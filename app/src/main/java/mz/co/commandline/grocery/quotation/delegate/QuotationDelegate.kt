package mz.co.commandline.grocery.quotation.delegate

import mz.co.commandline.grocery.quotation.dto.QuotationDTO

interface QuotationDelegate {
    fun quotation(): QuotationDTO
    fun selectItem()
    fun quote()
}