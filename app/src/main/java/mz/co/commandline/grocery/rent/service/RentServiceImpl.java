package mz.co.commandline.grocery.rent.service;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.rent.dto.RentDTO;
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO;
import mz.co.commandline.grocery.rent.dto.RentReport;
import mz.co.commandline.grocery.rent.dto.RentsDTO;
import mz.co.commandline.grocery.rent.dto.ReturnItemDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentServiceImpl extends AbstractService implements RentService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public RentServiceImpl() {
    }

    @Override
    public RentResource getResource() {
        return retrofitService.getResource(RentResource.class);
    }

    @Override
    public void registService(RentDTO rentDTO, final ResponseListner<RentDTO> responseListner) {
        getResource().registRent(rentDTO).enqueue(new Callback<RentDTO>() {
            @Override
            public void onResponse(Call<RentDTO> call, Response<RentDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<RentDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findPendingPaymentsRentsByCustomer(String customerUuid, final ResponseListner<RentsDTO> responseListner) {
        getResource().findPendingPaymentsRentsByCustomer(customerUuid).enqueue(new Callback<RentsDTO>() {
            @Override
            public void onResponse(Call<RentsDTO> call, Response<RentsDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<RentsDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void makePayment(final RentPaymentDTO rentPaymentDTO, final ResponseListner<RentPaymentDTO> responseListner) {
        getResource().makePayment(rentPaymentDTO).enqueue(new Callback<RentPaymentDTO>() {
            @Override
            public void onResponse(Call<RentPaymentDTO> call, Response<RentPaymentDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<RentPaymentDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void fetchPendingDevolutionsRentsByCustomer(String customerUuid, final ResponseListner<RentsDTO> responseListner) {
        getResource().fetchPendingDevolutionsRentsByCustomer(customerUuid).enqueue(new Callback<RentsDTO>() {
            @Override
            public void onResponse(Call<RentsDTO> call, Response<RentsDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<RentsDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void returnItems(List<ReturnItemDTO> returnItemsDTO, final ResponseListner<List<ReturnItemDTO>> responseListner) {
        getResource().returnItems(returnItemsDTO).enqueue(new Callback<List<ReturnItemDTO>>() {
            @Override
            public void onResponse(Call<List<ReturnItemDTO>> call, Response<List<ReturnItemDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<ReturnItemDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void getQuotationFile(String fileName, final ResponseListner<ResponseBody> responseListner) {
        getResource().getQuotationFile(fileName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void processtQuotation(final RentDTO rentDTO, final ResponseListner<RentReport> responseListner) {
        getResource().processtQuotation(rentDTO).enqueue(new Callback<RentReport>() {
            @Override
            public void onResponse(Call<RentReport> call, Response<RentReport> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<RentReport> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
