package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.StockDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StockResource {

    @GET("stocks/by-grocery-and-product")
    Call<List<StockDTO>> findStocksByGroceyAndProduct(@Query("groceryUuid") String groceryUuid, @Query("productUuid") String productUuid);

    @GET("stocks/by-grocery/{groceryUuid}")
    Call<List<StockDTO>> findAllStocksByGrocery(@Path("groceryUuid") String groceryUuid);

    @GET("stocks/by-grocery-and-sale-period")
    Call<List<StockDTO>> findLowStocksByGroceryAndSalePeriod(@Query("groceryUuid") String groceryUuid, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("stocks/not-in-this-grocery-by-product")
    Call<List<StockDTO>> findProductStocksNotInThisGroceryByProduct(@Query("groceryUuid") String groceryUuid, @Query("productUuid") String productUuid);

    @GET("stocks/in-analysis")
    Call<List<StockDTO>> findStocksInAnalysis(@Query("unitUuid") String unitUuid);

    @POST("stocks/regularize")
    Call<StockDTO> regularizeStock(@Body StockDTO stockDTO);
}
