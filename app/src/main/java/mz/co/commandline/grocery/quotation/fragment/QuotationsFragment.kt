package mz.co.commandline.grocery.quotation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentQuotationsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.adapter.QuotationAdapter
import mz.co.commandline.grocery.quotation.delegate.QuotationDelegate
import mz.co.commandline.grocery.quotation.dto.QuotationDTO

class QuotationsFragment : BaseFragment(), ClickListner<QuotationDTO> {

    private var _binding: FragmentQuotationsBinding? = null
    private val binding get() = _binding!!

    private var _delegate: QuotationDelegate? = null
    private val delegate get() = _delegate!!

    override fun getResourceId(): Int {
        return R.layout.fragment_quotations
    }

    override fun onCreateView() {
        _delegate = activity as QuotationDelegate
        val adapter = QuotationAdapter(activity, delegate.quotations())
        adapter.setItemClickListner(this)

        val quotationsRecycleView = binding.quotationsRecycleView
        quotationsRecycleView.adapter = adapter
        quotationsRecycleView.addItemDecoration(DividerItemDecoration(quotationsRecycleView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentQuotationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.quotations)
    }

    override fun onClickListner(quotation: QuotationDTO?) {
        delegate.selectedQuotation(quotation)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _delegate = null
    }
}