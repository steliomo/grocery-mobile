package mz.co.commandline.grocery.contract.service

import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.contract.dto.ContractPaymentDTO
import mz.co.commandline.grocery.contract.dto.ContractsDTO
import mz.co.commandline.grocery.generics.dto.EnumsDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContractResource {

    @POST("contracts")
    fun registContract(@Body contractDTO: ContractDTO): Call<ContractDTO>

    @GET("contracts/types")
    fun getContractType(): Call<EnumsDTO>

    @GET("contracts/by-customer")
    fun findPendigPaymentContractsByCustomer(@Query("customerUuid") customerUuid: String, @Query("currentDate") currentDate: String): Call<ContractsDTO>

    @POST("contracts/contract-payment")
    fun registContractPayment(@Body contractPaymentDTO: ContractPaymentDTO): Call<ContractPaymentDTO>
}
