package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.stock.model.Stock;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StockResource {

    @GET("stocks/by-product/{productUuid}")
    Call<List<Stock>> findStocksByProduct(@Path("productUuid") String productUuid);

    @GET("stocks/all")
    Call<List<Stock>> findAllStocks();

    @PUT("stocks/update-stocks-and-prices")
    Call<Void> updateStocksAndPrices(@Body List<Stock> stocks);

}
