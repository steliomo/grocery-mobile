package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.product.fragment.ProductFragment;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.sale.fragment.SaleRegistFragment;

public class SaleActivity extends BaseAuthActivity implements SaleDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ProductService productService;

    private FragmentManager fragmentManager;

    private ProgressDialog progressBar;

    List<Product> products;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_sale);

        toolbar.setTitle(R.string.sale_regist);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    private void showFragment(Fragment fragment, boolean onStack) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.sale_activity_frame_layout, fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void addItem() {
        progressBar.show();

        productService.findAllProducts(new ResponseListner<List<Product>>() {
            @Override
            public void success(List<Product> response) {
                progressBar.dismiss();
                products = response;
                showFragment(new ProductFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                Log.e("PRODUCTS", message);
            }
        });

    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void selectedProduct(Product product) {

    }
}
