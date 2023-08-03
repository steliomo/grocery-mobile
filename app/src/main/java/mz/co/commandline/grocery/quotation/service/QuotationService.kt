package mz.co.commandline.grocery.quotation.service

import mz.co.commandline.grocery.generics.listner.ResponseListner
import mz.co.commandline.grocery.quotation.dto.QuotationDTO
import retrofit2.http.Path

interface QuotationService {
    fun issueQuotation(quotationDTO: QuotationDTO, responseListner: ResponseListner<QuotationDTO>)

    fun fetchQuotationsByCustomer(@Path("customerUuid") customerUuid: String, responseListner: ResponseListner<List<QuotationDTO>>)

    fun reIssueQuotation(quotationDTO: QuotationDTO, responseListner: ResponseListner<QuotationDTO>)
}
