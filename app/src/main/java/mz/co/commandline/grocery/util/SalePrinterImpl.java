package mz.co.commandline.grocery.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;

public class SalePrinterImpl implements SalePrinter {

    private BluetoothPrinter printer;

    public SalePrinterImpl() {
        printer = new BluetoothPrinter();
        printer.findDevice();
        printer.openConnection();
    }

    @Override
    public void printReceipt(SaleDTO saleDTO, Bitmap bitmap) {

        printer.printImage(bitmap);
        printer.printNewLine();
        printer.printBoldCenterText(saleDTO.getGrocery().getName());
        printer.printBoldCenterText(saleDTO.getGrocery().getPhoneNumber());

        printer.printNewLine();
        printer.printText("Data: " + saleDTO.getSaleDate());

        printer.lineDivision('-');

        for (SaleItemDTO saleItemDTO : saleDTO.getItems()) {
            printer.printText(saleItemDTO.getSaleableItemDTO().getName());
            printer.printText(saleItemDTO.getQuantity().toString() + " x " + saleItemDTO.getSaleableItemDTO().getSalePrice(), FormatterUtil.mtFormat(saleItemDTO.getTotal()));
        }

        printer.lineDivision('-');
        printer.printBoldText("Total", FormatterUtil.mtFormat(saleDTO.getTotalSale()));
        printer.printNewLine();
        printer.printText("Obrigado pela preferencia!");
        printer.printNewLine();
        printer.printNewLine();
    }

    @Override
    public void closeConnection() {
        printer.closeConnection();
    }

    @Override
    public boolean hasDevice() {
        return printer.getBluetoothDevice() != null;
    }
}
