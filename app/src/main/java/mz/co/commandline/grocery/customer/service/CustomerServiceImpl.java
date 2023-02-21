package mz.co.commandline.grocery.customer.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerServiceImpl extends AbstractService implements CustomerService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public CustomerServiceImpl() {
    }

    @Override
    public void registCustomer(CustomerDTO customerDTO, final ResponseListner<CustomerDTO> responseListner) {

        getResource().registCustomer(customerDTO).enqueue(new Callback<CustomerDTO>() {
            @Override
            public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomerDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersByUnit(String unitUuid, int currentPage, int maxResult, final ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersByUnit(unitUuid, currentPage, maxResult).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithPendingPaymentsByUnit(String unitUuid, int currentPage, int maxResult, final ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithPendingPaymentsByUnit(unitUuid, currentPage, maxResult).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, int currentPage, int maxResult, final ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, currentPage, maxResult).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithContractPendingPaymentByUnit(String unitUuid, int currentPage, int maxResult, String currentDate, final ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithContractPendingPaymentByUnit(unitUuid, currentPage, maxResult, currentDate).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersSaleWithPendindOrIncompletePaymentByUnit(String unitUuid, final ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersSaleWithPendindOrIncompletePaymentByUnit(unitUuid).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(unitUuid).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithIssuedGuidesByTypeAndUnit(String guideType, String unitUuid, ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithIssuedGuidesByTypeAndUnit(guideType, unitUuid).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithPaymentsByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithPaymentsByUnit(unitUuid).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(unitUuid).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findCustomersWithDeliveredGuidesByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner) {
        getResource().findCustomersWithDeliveredGuidesByUnit(unitUuid).enqueue(new Callback<CustomersDTO>() {
            @Override
            public void onResponse(Call<CustomersDTO> call, Response<CustomersDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<CustomersDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public CustomerResource getResource() {
        return retrofitService.getResource(CustomerResource.class);
    }
}
