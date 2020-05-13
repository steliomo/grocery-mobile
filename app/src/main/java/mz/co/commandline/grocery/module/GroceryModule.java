package mz.co.commandline.grocery.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mz.co.commandline.grocery.expense.service.ExpenseService;
import mz.co.commandline.grocery.expense.service.ExpenseServiceImpl;
import mz.co.commandline.grocery.expense.service.ExpenseTypeService;
import mz.co.commandline.grocery.expense.service.ExpenseTypeServiceImpl;
import mz.co.commandline.grocery.infra.SharedPreferencesManager;
import mz.co.commandline.grocery.inventory.service.InventoryService;
import mz.co.commandline.grocery.inventory.service.InventoryServiceImpl;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.product.service.ProductServiceImpl;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.sale.service.SaleServiceImpl;
import mz.co.commandline.grocery.service.RetrofitService;
import mz.co.commandline.grocery.service.RetrofitServiceImpl;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.stock.service.StockServiceImpl;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.user.service.UserServiceImpl;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;

@Module
public class GroceryModule {

    private Context context;

    public GroceryModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public RetrofitService provideRetrofitService(RetrofitServiceImpl retrofitService) {
        return retrofitService;
    }

    @Singleton
    @Provides
    public SharedPreferencesManager provideSharedPreferencesManager() {
        return new SharedPreferencesManager(context);
    }

    @Provides
    public ProductService provideProductService(ProductServiceImpl productService) {
        return productService;
    }

    @Provides
    public UserService provideUserService(UserServiceImpl userService) {
        return userService;
    }

    @Provides
    public StockService provideStockService(StockServiceImpl stockService) {
        return stockService;
    }

    @Provides
    public SaleService provideSaleService(SaleServiceImpl saleService) {
        return saleService;
    }

    @Provides
    public InventoryService provideInventoryService(InventoryServiceImpl inventoryService) {
        return inventoryService;
    }

    @Provides
    public ExpenseTypeService provideExpenseTypeService(ExpenseTypeServiceImpl expenseTypeService) {
        return expenseTypeService;
    }

    @Provides
    public ExpenseService provideExpenseService(ExpenseServiceImpl expenseService) {
        return expenseService;
    }
}
