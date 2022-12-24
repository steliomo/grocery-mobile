package mz.co.commandline.grocery.sale.dto

import java.math.BigDecimal

class SalePaymentDTO(private var saleUuid: String, private var paymentValue: BigDecimal, private var paymentDate: String) {}