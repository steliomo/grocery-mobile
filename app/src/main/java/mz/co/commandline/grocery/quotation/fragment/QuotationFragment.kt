package mz.co.commandline.grocery.quotation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentQuotationBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.quotation.adapter.QuotationItemAdapter
import mz.co.commandline.grocery.quotation.delegate.QuotationDelegate
import mz.co.commandline.grocery.util.FormatterUtil


class QuotationFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentQuotationBinding? = null
    private val binding get() = _binding!!

    private var _delegate: QuotationDelegate? = null
    private val delegate get() = _delegate!!

    override fun getResourceId(): Int {
        return R.layout.fragment_quotation
    }

    override fun onCreateView() {
        _delegate = activity as QuotationDelegate
        val quotation = delegate.quotation()

        binding.quotationGrandTotal.text = FormatterUtil.mtFormat(quotation.totalValue);
        val quotationRecyclerView = binding.quotationRecyclerView

        quotationRecyclerView.adapter = QuotationItemAdapter(activity, quotation.items)
        quotationRecyclerView.addItemDecoration(DividerItemDecoration(quotationRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.quotationSelectItemBtn.setOnClickListener(this)
        binding.quotationQuoteBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.quotation);
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentQuotationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _delegate = null
    }

    override fun onClick(view: View) {
        if (view.id == binding.quotationSelectItemBtn.id) {
            delegate.selectItem()
            return
        }
        
        delegate.quote()
    }
}