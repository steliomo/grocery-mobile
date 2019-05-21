package mz.co.commandline.grocery.product.model;

import mz.co.commandline.grocery.model.GenericModel;

public class Product extends GenericModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
