package mz.co.commandline.grocery.saleable.delegate;

import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.saleable.dto.SaleableDTO;
import mz.co.commandline.grocery.saleable.dto.ActionType;

public interface SaleableItemActionDelegate extends SaleableItemDelegate {

    void selectedAction(ActionType actionType);

    String getTiTle();

    String getActionName();

    void cancel();

    void selectItem();

    SaleableDTO getSaleable();

    void addSaleableItem(SaleableItemDTO saleableItem);

    void execute();

    void prepareSaleable();

    ActionType getActionType();
}
