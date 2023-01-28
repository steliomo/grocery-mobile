package mz.co.commandline.grocery.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mz.co.commandline.grocery.contract.service.ContractService;
import mz.co.commandline.grocery.contract.service.ContractServiceImpl;
import mz.co.commandline.grocery.customer.service.CustomerService;
import mz.co.commandline.grocery.customer.service.CustomerServiceImpl;
import mz.co.commandline.grocery.expense.service.ExpenseService;
import mz.co.commandline.grocery.expense.service.ExpenseServiceImpl;
import mz.co.commandline.grocery.expense.service.ExpenseTypeService;
import mz.co.commandline.grocery.expense.service.ExpenseTypeServiceImpl;
import mz.co.commandline.grocery.files.FileService;
import mz.co.commandline.grocery.files.FileServiceImpl;
import mz.co.commandline.grocery.infra.SharedPreferencesManager;
import mz.co.commandline.grocery.inventory.service.InventoryService;
import mz.co.commandline.grocery.inventory.service.InventoryServiceImpl;
import mz.co.commandline.grocery.item.service.ItemService;
import mz.co.commandline.grocery.item.service.ItemServiceImpl;
import mz.co.commandline.grocery.payment.service.PaymentService;
import mz.co.commandline.grocery.payment.service.PaymentServiceImpl;
import mz.co.commandline.grocery.rent.service.RentService;
import mz.co.commandline.grocery.rent.service.RentServiceImpl;
import mz.co.commandline.grocery.saleable.service.SaleableItemService;
import mz.co.commandline.grocery.saleable.service.SaleableItemServiceImpl;
import mz.co.commandline.grocery.item.service.ProductService;
import mz.co.commandline.grocery.item.service.ProductServiceImpl;
import mz.co.commandline.grocery.item.service.ServiceService;
import mz.co.commandline.grocery.item.service.ServiceServiceImpl;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.sale.service.SaleServiceImpl;
import mz.co.commandline.grocery.saleable.service.SaleableService;
import mz.co.commandline.grocery.saleable.service.SaleableServiceImpl;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.generics.service.RetrofitServiceImpl;
import mz.co.commandline.grocery.saleable.service.ServiceItemService;
import mz.co.commandline.grocery.saleable.service.ServiceItemServiceImpl;
import mz.co.commandline.grocery.saleable.service.StockService;
import mz.co.commandline.grocery.saleable.service.StockServiceImpl;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.user.service.UserServiceImpl;

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

    @Provides
    public ItemService provideItemService(ItemServiceImpl itemService) {
        return itemService;
    }

    @Provides
    public SaleableItemService provideSalableItemService(SaleableItemServiceImpl salableItemService) {
        return salableItemService;
    }

    @Provides
    public ServiceService provideServiceService(ServiceServiceImpl serviceService) {
        return serviceService;
    }

    @Provides
    public ServiceItemService provideServiceItemService(ServiceItemServiceImpl serviceItemService) {
        return serviceItemService;
    }

    @Provides
    public SaleableService provideSaleableService(SaleableServiceImpl saleableService) {
        return saleableService;
    }

    @Provides
    public PaymentService providePaymentService(PaymentServiceImpl paymentService) {
        return paymentService;
    }

    @Provides
    public CustomerService provideCustomerService(CustomerServiceImpl customerService) {
        return customerService;
    }

    @Provides
    public RentService provideRentService(RentServiceImpl rentService) {
        return rentService;
    }

    @Provides
    public ContractService provideContractService(ContractServiceImpl contractService) {
        return contractService;
    }

    @Provides
    public FileService provideFileService(FileServiceImpl fileService) {
        return fileService;
    }
}
