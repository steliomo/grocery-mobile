package mz.co.commandline.grocery.quotation.delegate

import mz.co.commandline.grocery.quotation.dto.QuotationDTO

interface QuotationDelegate {
    fun quotation(): QuotationDTO
    fun selectItem()
    fun quote()
    fun quotations(): List<QuotationDTO>
    fun selectedQuotation(quotation: QuotationDTO?)
    fun reIssueQuotation()
}