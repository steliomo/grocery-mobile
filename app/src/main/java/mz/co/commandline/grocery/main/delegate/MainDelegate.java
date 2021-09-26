package mz.co.commandline.grocery.main.delegate;

import java.util.List;

import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.user.dto.UserRole;

public interface MainDelegate extends MenuDelegate {

    SalesDTO getSales();

}
