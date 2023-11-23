package mz.co.commandline.grocery.sale.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO;
import mz.co.commandline.grocery.sale.dto.SaleStatus;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleServiceImpl extends AbstractService implements SaleService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public SaleServiceImpl() {
    }


    @Override
    public void registSale(SaleDTO sale, final ResponseListner<SaleDTO> responseListner) {
        getResource().registeSale(sale).enqueue(new Callback<SaleDTO>() {
            @Override
            public void onResponse(Call<SaleDTO> call, Response<SaleDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findSalesPerPeriod(String groceryUuid, String startDate, String endDate, final ResponseListner<SalesDTO> responseListner) {
        getResource().findSalesPerPeriod(groceryUuid, startDate, endDate).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findMonthlyalesPerPeriod(String groceryUuid, String startDate, String endDate, final ResponseListner<SalesDTO> responseListner) {
        getResource().findMonthlySalesPerPeriod(groceryUuid, startDate, endDate).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findPendingOrIncompleteSalesByCustomer(String customerUuid, final ResponseListner<SalesDTO> responseListner) {
        getResource().findPendingOrIncompleteSalesByCustomer(customerUuid).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void salePayment(SalePaymentDTO salePaymentDTO, final ResponseListner<SalePaymentDTO> responseListner) {
        getResource().salePayment(salePaymentDTO).enqueue(new Callback<SalePaymentDTO>() {
            @Override
            public void onResponse(Call<SalePaymentDTO> call, Response<SalePaymentDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalePaymentDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(String customerUuid, ResponseListner<SalesDTO> responseListner) {
        getResource().fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(customerUuid).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void fetchSalesWithDeliveryGuidesByCustomer(String customerUuid, ResponseListner<SalesDTO> responseListner) {
        getResource().fetchSalesWithDeliveryGuidesByCustomer(customerUuid).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void processOpenTable(SaleDTO table, ResponseListner<SaleDTO> responseListner) {
        getResource().processOpenTable(table).enqueue(new Callback<SaleDTO>() {
            @Override
            public void onResponse(Call<SaleDTO> call, Response<SaleDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void fetchOpenedTables(String unitUuid, ResponseListner<SalesDTO> responseListner) {
        getResource().fetchOpenedTables(unitUuid).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void registAddedItems(SaleDTO table, ResponseListner<SaleDTO> responseListner) {
        getResource().registAddedItems(table).enqueue(new Callback<SaleDTO>() {
            @Override
            public void onResponse(Call<SaleDTO> call, Response<SaleDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void fetchOpenedTableByUuid(String tableUuid, ResponseListner<SaleDTO> responseListner) {
        getResource().fetchOpenedTableByUuid(tableUuid).enqueue(new Callback<SaleDTO>() {
            @Override
            public void onResponse(Call<SaleDTO> call, Response<SaleDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void sendTableBill(SaleDTO table, ResponseListner<SaleDTO> responseListner) {
        getResource().sendTableBill(table).enqueue(new Callback<SaleDTO>() {
            @Override
            public void onResponse(Call<SaleDTO> call, Response<SaleDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public SaleResource getResource() {
        return retrofitService.getResource(SaleResource.class);
    }
}
