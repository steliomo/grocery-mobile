package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockServiceImpl extends AbstractService implements StockService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public StockServiceImpl() {
    }

    @Override
    public StockResource getResource() {
        return retrofitService.getResource(StockResource.class);
    }

    @Override
    public void findProductStocksByGroceryAndProduct(UnitDTO groceryDTO, ProductDTO productDTO, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findStocksByGroceyAndProduct(groceryDTO.getUuid(), productDTO.getUuid()).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findAllStocksByGrocery(UnitDTO groceryDTO, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findAllStocksByGrocery(groceryDTO.getUuid()).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findLowStocksByGroceryAndSalePeriod(UnitDTO groceryDTO, String startDate, String endDate, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findLowStocksByGroceryAndSalePeriod(groceryDTO.getUuid(), startDate, endDate).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findProductStocksNotInThisGroceryByProduct(UnitDTO grocery, ProductDTO productDTO, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findProductStocksNotInThisGroceryByProduct(grocery.getUuid(), productDTO.getUuid()).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findStocksInAnalysis(String unitUuid, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findStocksInAnalysis(unitUuid).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void regularizeStock(StockDTO stockDTO, final ResponseListner<StockDTO> responseListner) {
        getResource().regularizeStock(stockDTO).enqueue(new Callback<StockDTO>() {
            @Override
            public void onResponse(Call<StockDTO> call, Response<StockDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<StockDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
