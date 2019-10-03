package mz.co.commandline.grocery.module;


import javax.inject.Singleton;

import dagger.Component;
import mz.co.commandline.grocery.activities.BaseAuthActivity;
import mz.co.commandline.grocery.activities.LoginActivity;
import mz.co.commandline.grocery.activities.MainActivity;
import mz.co.commandline.grocery.activities.ReportActivity;
import mz.co.commandline.grocery.activities.SaleActivity;
import mz.co.commandline.grocery.activities.StockActivity;

@Singleton
@Component(modules = GroceryModule.class)
public interface GroceryComponent {

    void inject(BaseAuthActivity activity);

    void inject(MainActivity activity);

    void inject(SaleActivity activity);

    void inject(ReportActivity activity);

    void inject(StockActivity activity);

    void inject(LoginActivity activity);
}
