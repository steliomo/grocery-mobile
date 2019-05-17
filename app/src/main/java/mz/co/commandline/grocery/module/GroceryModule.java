package mz.co.commandline.grocery.module;

import android.content.Context;

import dagger.Module;

@Module
public class GroceryModule {

    private Context contenxt;

    public GroceryModule(Context contenxt) {
        this.contenxt = contenxt;
    }
}
