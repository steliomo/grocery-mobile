package mz.co.commandline.grocery.grocery.model;

import mz.co.commandline.grocery.model.GenericModel;

public class Grocery extends GenericModel {

    @Override
    public String toString() {
        return getId() + "_" + getUuid();
    }
}
