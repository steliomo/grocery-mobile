package mz.co.commandline.grocery.quotation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentQuotationDetailsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.quotation.adapter.ItemAdapter
import mz.co.commandline.grocery.quotation.delegate.QuotationDelegate
import mz.co.commandline.grocery.util.FormatterUtil


class QuotationDetailsFragment : BaseFragment() {

    private var _binding: FragmentQuotationDetailsBinding? = null
    private val binding get() = _binding!!


    override fun getResourceId(): Int {
        return R.layout.fragment_quotation_details
    }

    override fun onCreateView() {
        val delegate = activity as QuotationDelegate
        val quotation = delegate.quotation()

        binding.quotationDetailsDate.text = quotation.issueDate
        binding.quotationDetailsCode.text = quotation.code
        binding.quotationDetailsDiscount.text = FormatterUtil.mtFormat(quotation.discount)
        binding.quotationDetailsTotalValue.text = FormatterUtil.mtFormat(quotation.totalValue)

        val quotationItemRecyclerView = binding.quotationItemRecyclerView

        quotationItemRecyclerView.adapter = ItemAdapter(activity, quotation.items)
        quotationItemRecyclerView.addItemDecoration(DividerItemDecoration(quotationItemRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.reIssueQuotationBtn.setOnClickListener { delegate.reIssueQuotation() }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentQuotationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTitle(): String {
        return getString(R.string.quotation_details)
    }
}