package mz.co.commandline.grocery.infra;

import android.app.Application;

import mz.co.commandline.grocery.module.DaggerGroceryComponent;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.module.GroceryModule;

public class GroceryApplication extends Application {

    private GroceryComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerGroceryComponent.builder().groceryModule(new GroceryModule(this)).build();
    }

    public GroceryComponent getComponent() {
        return component;
    }
}
