package mz.co.commandline.grocery.saleable.service;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.saleable.dto.SaleableDTO;

public interface SaleableService {

    void addSaleable(SaleableDTO saleable, ResponseListner<SaleableDTO> responseListner);

    void updateSaleable(SaleableDTO saleable, ResponseListner<SaleableDTO> responseListner);

}
