package mz.co.commandline.grocery.expense.dto

import java.math.BigDecimal

data class ExpenseReport(val name: String, val expenseValue: BigDecimal)