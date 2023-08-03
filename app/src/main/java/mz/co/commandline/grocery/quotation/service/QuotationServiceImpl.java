package mz.co.commandline.grocery.quotation.service;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.quotation.dto.QuotationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationServiceImpl extends AbstractService implements QuotationService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public QuotationServiceImpl() {
    }

    @Override
    public QuotationResource getResource() {
        return retrofitService.getResource(QuotationResource.class);
    }

    @Override
    public void issueQuotation(QuotationDTO quotationDTO, ResponseListner<QuotationDTO> responseListner) {
        getResource().issueQuotation(quotationDTO).enqueue(new Callback<QuotationDTO>() {
            @Override
            public void onResponse(Call<QuotationDTO> call, Response<QuotationDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<QuotationDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void fetchQuotationsByCustomer(@NotNull String customerUuid, @NotNull ResponseListner<List<QuotationDTO>> responseListner) {
        getResource().fetchQuotationsByCustomer(customerUuid).enqueue(new Callback<List<QuotationDTO>>() {
            @Override
            public void onResponse(Call<List<QuotationDTO>> call, Response<List<QuotationDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<QuotationDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void reIssueQuotation(@NotNull QuotationDTO quotationDTO, @NotNull ResponseListner<QuotationDTO> responseListner) {
        getResource().reIssueQuotation(quotationDTO).enqueue(new Callback<QuotationDTO>() {
            @Override
            public void onResponse(Call<QuotationDTO> call, Response<QuotationDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<QuotationDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
