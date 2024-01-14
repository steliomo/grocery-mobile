package mz.co.commandline.grocery.quotation.delegate

import mz.co.commandline.grocery.quotation.dto.QuotationDTO

interface VisualiseQuotationDelegate {

    fun quotations(): List<QuotationDTO>
    fun selectedQuotation(quotation: QuotationDTO?)
    fun reIssueQuotation()
}