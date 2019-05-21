package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.stock.model.Stock;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StockResource {

    @GET("stocks/by-product/{product}")
    Call<List<Stock>> findStocksByProducts(@Path("product") String product);

}
