package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.stock.dto.StockDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StockResource {

    @GET("stocks/by-grocery-and-product")
    Call<List<StockDTO>> findStocksByGroceyAndProduct(@Query("groceryUuid") String groceryUuid, @Query("productUuid") String productUuid);

    @GET("stocks/by-grocery/{groceryUuid}")
    Call<List<StockDTO>> findAllStocksByGrocery(@Path("groceryUuid") String groceryUuid);

    @PUT("stocks/update-stocks-and-prices")
    Call<Void> updateStocksAndPrices(@Body List<StockDTO> stocks);

    @GET("stocks/by-grocery-and-sale-period")
    Call<List<StockDTO>> findLowStocksByGroceryAndSalePeriod(@Query("groceryUuid") String groceryUuid, @Query("startDate") String startDate, @Query("endDate") String endDate);
}
