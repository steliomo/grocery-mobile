package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ServiceDTO;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;

public interface ServiceItemService {

    void findServiceItemsByServiceAndUnit(ServiceDTO service, GroceryDTO unit, ResponseListner<List<ServiceItemDTO>> responseListner);

    void findServiceItemsNotInThisUnitByService(ServiceDTO service, GroceryDTO unit, ResponseListner<List<ServiceItemDTO>> responseListner);
}
