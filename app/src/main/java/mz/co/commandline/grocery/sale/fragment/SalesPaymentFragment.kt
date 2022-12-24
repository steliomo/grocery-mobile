package mz.co.commandline.grocery.sale.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentSalesPaymentBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.sale.adapter.SaleAdapter
import mz.co.commandline.grocery.sale.delegate.SaleDelegate
import mz.co.commandline.grocery.sale.dto.SaleDTO


class SalesPaymentFragment : BaseFragment(), ClickListner<SaleDTO> {

    private var _binding: FragmentSalesPaymentBinding? = null
    private val binding get() = _binding!!

    private var delegate: SaleDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_sales_payment
    }

    override fun onCreateView() {
        val salesPaymentRecycleView = binding.salesPaymentRecycleView;

        delegate = activity as SaleDelegate

        val saleAdapter = SaleAdapter(activity, delegate!!.pendingOrIncompleteSales);
        saleAdapter.setItemClickListner(this)
        salesPaymentRecycleView.adapter = saleAdapter
        salesPaymentRecycleView.addItemDecoration(DividerItemDecoration(salesPaymentRecycleView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentSalesPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.sales)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickListner(saleDTO: SaleDTO?) {
        delegate?.selectedSale(saleDTO)
    }
}