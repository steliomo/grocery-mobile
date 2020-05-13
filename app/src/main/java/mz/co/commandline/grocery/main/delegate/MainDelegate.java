package mz.co.commandline.grocery.main.delegate;

import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.user.dto.UserRole;

public interface MainDelegate {

    UserRole getUserRole();

    SalesDTO getSales();
}
