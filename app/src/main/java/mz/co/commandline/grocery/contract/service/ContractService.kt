package mz.co.commandline.grocery.contract.service

import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.contract.dto.ContractPaymentDTO
import mz.co.commandline.grocery.contract.dto.ContractsDTO
import mz.co.commandline.grocery.generics.dto.EnumsDTO
import mz.co.commandline.grocery.generics.listner.ResponseListner
import java.util.*

interface ContractService {
    fun registContract(contractDTO: ContractDTO, responseListner: ResponseListner<ContractDTO>)

    fun getContractTypes(responseListner: ResponseListner<EnumsDTO>)

    fun findPendigPaymentContractsByCustomer(customerUuid: String, currentDate: String, responseListner: ResponseListner<ContractsDTO>)

    fun registContractPayment(contractPaymentDTO: ContractPaymentDTO, responseListner: ResponseListner<ContractPaymentDTO>)
}