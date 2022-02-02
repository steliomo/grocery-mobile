package mz.co.commandline.grocery.contract.service;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import mz.co.commandline.grocery.contract.dto.ContractDTO;
import mz.co.commandline.grocery.contract.dto.ContractPaymentDTO;
import mz.co.commandline.grocery.contract.dto.ContractsDTO;
import mz.co.commandline.grocery.contract.fragment.ContractsFragment;
import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractServiceImpl extends AbstractService implements ContractService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ContractServiceImpl() {
    }

    @Override
    public void registContract(ContractDTO contractDTO, final ResponseListner<ContractDTO> responseListner) {

        getResource().registContract(contractDTO).enqueue(new Callback<ContractDTO>() {
            @Override
            public void onResponse(Call<ContractDTO> call, Response<ContractDTO> response) {

                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<ContractDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public ContractResource getResource() {
        return retrofitService.getResource(ContractResource.class);
    }

    @Override
    public void getContractTypes(@NotNull final ResponseListner<EnumsDTO> responseListner) {
        getResource().getContractType().enqueue(new Callback<EnumsDTO>() {
            @Override
            public void onResponse(Call<EnumsDTO> call, Response<EnumsDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<EnumsDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findPendigPaymentContractsByCustomer(@NotNull String customerUuid, @NotNull String currentDate, final ResponseListner<ContractsDTO> responseListner) {
        getResource().findPendigPaymentContractsByCustomer(customerUuid, currentDate).enqueue(new Callback<ContractsDTO>() {
            @Override
            public void onResponse(Call<ContractsDTO> call, Response<ContractsDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<ContractsDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void registContractPayment(@NotNull ContractPaymentDTO contractPaymentDTO, @NotNull final ResponseListner<ContractPaymentDTO> responseListner) {
        getResource().registContractPayment(contractPaymentDTO).enqueue(new Callback<ContractPaymentDTO>() {
            @Override
            public void onResponse(Call<ContractPaymentDTO> call, Response<ContractPaymentDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<ContractPaymentDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
