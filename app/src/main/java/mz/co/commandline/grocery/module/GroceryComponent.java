package mz.co.commandline.grocery.module;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = GroceryModule.class)
public interface GroceryComponent {
}
