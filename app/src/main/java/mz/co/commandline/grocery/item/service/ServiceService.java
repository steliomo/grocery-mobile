package mz.co.commandline.grocery.item.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ServiceDTO;

public interface ServiceService {

    void findServiceByUnit(UnitDTO unit, ResponseListner<List<ServiceDTO>> listResponseListner);

    void findServicesNotInThisUnit(UnitDTO unit, ResponseListner<List<ServiceDTO>> listResponseListner);
}
