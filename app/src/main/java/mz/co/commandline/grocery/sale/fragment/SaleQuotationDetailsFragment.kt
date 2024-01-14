package mz.co.commandline.grocery.sale.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentSaleQuotationDetailsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.quotation.delegate.IssueQuotationDelegate
import mz.co.commandline.grocery.quotation.delegate.QuotationDelegate
import mz.co.commandline.grocery.util.FormatterUtil

class SaleQuotationDetailsFragment : BaseFragment() {

    private var _binding: FragmentSaleQuotationDetailsBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_sale_quotation_details
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentSaleQuotationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateView() {
        val delegate = activity as QuotationDelegate
        val issueQuotationDelegate = activity as IssueQuotationDelegate

        val quotation = delegate.quotation()

        binding.saleQuotationDetailsCustomerName.text = quotation.customerDTO?.name
        binding.saleQuotationDetailsTotalAmountValue.text = FormatterUtil.mtFormat(quotation.totalValue)

        binding.saleQuotationDetailsEmitBtn.setOnClickListener { issueQuotationDelegate.issue() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTitle(): String {
        return getString(R.string.quotation_details)
    }
}