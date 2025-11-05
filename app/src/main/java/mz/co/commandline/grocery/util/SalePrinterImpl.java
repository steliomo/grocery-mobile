package mz.co.commandline.grocery.util;

import android.graphics.Bitmap;

import java.util.Date;

import mz.co.commandline.grocery.pos.dto.DebtDTO;
import mz.co.commandline.grocery.pos.dto.DebtItemDTO;
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
        printer.printBoldCenterText(saleDTO.getUnitDTO().getName());
        printer.printBoldCenterText(saleDTO.getUnitDTO().getPhoneNumber());

        printer.printNewLine();
        printer.printText("Data:", saleDTO.getSaleDate());

        printer.lineDivision('-');

        for (SaleItemDTO saleItemDTO : saleDTO.getItems()) {
            printer.printText(saleItemDTO.getSaleableItemDTO().getName());
            printer.printText(saleItemDTO.getQuantity().toString() + " x " + saleItemDTO.getSaleableItemDTO().getSalePrice(), FormatterUtil.mtFormat(saleItemDTO.getTotal()));
        }

        printer.lineDivision('-');
        printer.printBoldText("Total", FormatterUtil.mtFormat(saleDTO.getTotal()));
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
        return printer.getBluetoothDevice() != null && printer.getInputStream() != null;
    }

    @Override
    public void printDept(DebtDTO dept, Bitmap bitmap) {
        printer.printImage(bitmap);
        printer.printNewLine();
        printer.printBoldCenterText(dept.getCustomer().getName());
        printer.printBoldCenterText(dept.getCustomer().getContact());

        printer.printNewLine();
        printer.printText("Data da impressão:", DateUtil.format( new Date(), DateUtil.NORMAL_PATTERN));

        printer.lineDivision('-');

        for (DebtItemDTO deptItem : dept.getDebtItems()) {
            printer.printText("",deptItem.getDebtDate());
            printer.printText(deptItem.getName().replace("0.00 NA", ""));
            printer.printText(deptItem.getQuantity() + " x " + deptItem.getPrice(), FormatterUtil.mtFormat(deptItem.getDebtItemValue()));
        }

        printer.lineDivision('-');
        printer.printText("Sub-Total", FormatterUtil.mtFormat(dept.getTotalToPay()));
        printer.printText("Total Pago", FormatterUtil.mtFormat(dept.getTotalPaid()));
        printer.printBoldText("Total em Dívida", FormatterUtil.mtFormat(dept.getTotalInDebt()));
        printer.printNewLine();
        printer.printText("Obrigado pela preferencia!");
        printer.printNewLine();
        printer.printNewLine();
    }
}
