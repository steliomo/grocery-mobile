package mz.co.commandline.grocery.menu

import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.user.dto.UserRole

class MainMenuBuilder {

    private var menu: Menu? = null

    init {
        menu = Menu()
    }

    fun build(userRole: UserRole): Menu? {

        when (userRole) {
            UserRole.OPERATOR -> {
                menu!!.addMenuItem(MenuItem(R.string.sales, MenuItemType.SALE, R.mipmap.ic_sale))
                menu!!.addMenuItem(MenuItem(R.string.rents, MenuItemType.RENT, R.mipmap.ic_rents))
                menu!!.addMenuItem(MenuItem(R.string.contracts, MenuItemType.CONTRACT, R.mipmap.ic_contracts))
                menu!!.addMenuItem(MenuItem(R.string.expenses, MenuItemType.EXPENSE, R.mipmap.ic_expense))
                menu!!.addMenuItem(MenuItem(R.string.inventory, MenuItemType.INVENTORY, R.mipmap.ic_inventory))
                menu!!.addMenuItem(MenuItem(R.string.reports, MenuItemType.REPORT, R.mipmap.ic_report))
            }

            else -> {
                menu!!.addMenuItem(MenuItem(R.string.sales, MenuItemType.SALE, R.mipmap.ic_sale))
                menu!!.addMenuItem(MenuItem(R.string.rents, MenuItemType.RENT, R.mipmap.ic_rents))
                menu!!.addMenuItem(MenuItem(R.string.contracts, MenuItemType.CONTRACT, R.mipmap.ic_contracts))
                menu!!.addMenuItem(MenuItem(R.string.products_and_services, MenuItemType.STOCK, R.mipmap.ic_stock))
                menu!!.addMenuItem(MenuItem(R.string.expenses, MenuItemType.EXPENSE, R.mipmap.ic_expense))
                menu!!.addMenuItem(MenuItem(R.string.inventory, MenuItemType.INVENTORY, R.mipmap.ic_inventory))
                menu!!.addMenuItem(MenuItem(R.string.reports, MenuItemType.REPORT, R.mipmap.ic_report))
            }
        }

        return menu
    }
}