package mz.co.commandline.grocery.contract.delegate

import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.contract.dto.ContractPaymentDTO
import mz.co.commandline.grocery.generics.dto.EnumDTO
import mz.co.commandline.grocery.main.delegate.MenuDelegate

interface ContractDelegate : MenuDelegate {
    fun getContractTypes(): List<EnumDTO>
    fun getContract(): ContractDTO
    fun celebrateContract()
    fun registContract()
    fun getContracts(): List<ContractDTO>
    fun paymentDetails(contract: ContractDTO)
    fun registContractPayment(contractPaymentDTO: ContractPaymentDTO)
}