package mz.co.commandline.grocery.sale.fragment

import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.generics.fragment.BaseFragment


class SalePaymentFragment : BaseFragment() {

    override fun getResourceId(): Int {
        return R.layout.fragment_sale_payment
    }

    override fun onCreateView() {
    }

    override fun getTitle(): String {
        return getString(R.string.installment_payment)
    }

}