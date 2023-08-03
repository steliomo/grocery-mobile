package mz.co.commandline.grocery.quotation.service

import mz.co.commandline.grocery.quotation.dto.QuotationDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuotationResource {

    @POST("quotations/issue-quotation")
    fun issueQuotation(@Body quotationDTO: QuotationDTO): Call<QuotationDTO>

    @GET("quotations/fetch-quotations-by-customer/{customerUuid}")
    fun fetchQuotationsByCustomer(@Path("customerUuid") customerUuid: String): Call<List<QuotationDTO>>

    @POST("quotations/re-issue-quotation")
    fun reIssueQuotation(@Body quotationDTO: QuotationDTO): Call<QuotationDTO>
}