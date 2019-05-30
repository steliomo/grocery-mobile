package mz.co.commandline.grocery.module;


import javax.inject.Singleton;

import dagger.Component;
import mz.co.commandline.grocery.activities.BaseAuthActivity;
import mz.co.commandline.grocery.activities.MainActivity;
import mz.co.commandline.grocery.activities.ReportActivity;
import mz.co.commandline.grocery.activities.SaleActivity;

@Singleton
@Component(modules = GroceryModule.class)
public interface GroceryComponent {

    void inject(BaseAuthActivity activity);

    void inject(MainActivity activity);

    void inject(SaleActivity activity);

    void inject(ReportActivity activity);
}
