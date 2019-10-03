package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.stock.model.Stock;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StockResource {

    @GET("stocks/by-grocery-and-product")
    Call<List<Stock>> findStocksByGroceyAndProduct(@Query("groceryUuid") String groceryUuid, @Query("productUuid") String productUuid);

    @GET("stocks/by-grocery/{groceryUuid}")
    Call<List<Stock>> findAllStocksByGrocery(@Path("groceryUuid") String groceryUuid);

    @PUT("stocks/update-stocks-and-prices")
    Call<Void> updateStocksAndPrices(@Body List<Stock> stocks);
}
