package mz.co.commandline.grocery.stock.delegate;

public interface ProductsAndStocksDelegate {

    void addNewProducts();

    void updateStockAndPrices();

    String getTiTle();

    String getActionName();
}
