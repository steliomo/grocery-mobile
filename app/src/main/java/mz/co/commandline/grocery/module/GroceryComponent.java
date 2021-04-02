package mz.co.commandline.grocery.module;


import javax.inject.Singleton;

import dagger.Component;
import mz.co.commandline.grocery.activities.BaseAuthActivity;
import mz.co.commandline.grocery.activities.ExpenseActivity;
import mz.co.commandline.grocery.activities.InventoryActivity;
import mz.co.commandline.grocery.activities.LoginActivity;
import mz.co.commandline.grocery.activities.MainActivity;
import mz.co.commandline.grocery.activities.SaleableActivity;
import mz.co.commandline.grocery.activities.ReportActivity;
import mz.co.commandline.grocery.activities.SaleActivity;

@Singleton
@Component(modules = GroceryModule.class)
public interface GroceryComponent {

    void inject(BaseAuthActivity activity);

    void inject(MainActivity activity);

    void inject(SaleActivity activity);

    void inject(ReportActivity activity);

    void inject(SaleableActivity activity);

    void inject(LoginActivity activity);

    void inject(InventoryActivity activity);

    void inject(ExpenseActivity activity);
}
