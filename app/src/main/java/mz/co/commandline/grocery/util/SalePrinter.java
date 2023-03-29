package mz.co.commandline.grocery.util;

import android.graphics.Bitmap;

import mz.co.commandline.grocery.sale.dto.SaleDTO;

public interface SalePrinter {

    void printReceipt(SaleDTO saleDTO, Bitmap bitmap);

    void closeConnection();

    boolean hasDevice();
}
