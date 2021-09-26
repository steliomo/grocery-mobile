package mz.co.commandline.grocery.rent.delegate;

import java.util.List;

import mz.co.commandline.grocery.main.delegate.MenuDelegate;
import mz.co.commandline.grocery.rent.model.RentDTO;
import mz.co.commandline.grocery.rent.model.RentItemDTO;
import mz.co.commandline.grocery.rent.model.RentPaymentDTO;
import mz.co.commandline.grocery.rent.model.ReturnItemDTO;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemDelegate;

public interface RentDelegate extends MenuDelegate, SaleableItemDelegate {
    void selectItem();

    void addRentItem(RentItemDTO rentItemDTO);

    RentDTO getRent();

    void nextStep();

    void registRent();

    List<RentDTO> getRentsDTO();

    void selectedPendingRent(RentDTO rentDTO);

    void makePayment(RentPaymentDTO rentPaymentDTO);

    void selectedRentItem(RentItemDTO rentItemDTO);

    RentItemDTO getRentItemDTO();

    void addReturnItemDTO(ReturnItemDTO returnItemDTO);

    void returnItems();
}
