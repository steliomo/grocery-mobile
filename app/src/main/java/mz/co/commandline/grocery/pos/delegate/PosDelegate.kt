package mz.co.commandline.grocery.pos.delegate

import mz.co.commandline.grocery.item.delegate.ItemDelegate
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO
import mz.co.commandline.grocery.saleable.delegate.SaleableItemDelegate

interface PosDelegate : ItemDelegate, SaleableItemDelegate {

    fun openTable()

    fun processOpenTable(table: SaleDTO)

    fun getTables(): List<SaleDTO>

    fun selectedTable(table: SaleDTO?)

    fun getSelectedTable(): SaleDTO

    fun selectedMenuItem(iconId: Int)

    fun selectItem()

    fun cancel()

    fun addSaleItem(saleItem: SaleItemDTO)

    fun registAddedItems()

    fun registPayment(payment: SalePaymentDTO)

    fun print()

    fun sendToWhatsApp()
}