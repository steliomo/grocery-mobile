package mz.co.commandline.grocery.item.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ServiceDTO;

public interface ServiceService {

    void findServiceByUnit(GroceryDTO unit, ResponseListner<List<ServiceDTO>> listResponseListner);

    void findServicesNotInThisUnit(GroceryDTO unit, ResponseListner<List<ServiceDTO>> listResponseListner);
}
