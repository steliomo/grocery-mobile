package mz.co.commandline.grocery.inventory.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.contract.delegate.ContractDelegate
import mz.co.commandline.grocery.databinding.FragmentStockAnalysisBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.inventory.adapter.StockAnalysisAdapter
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate
import mz.co.commandline.grocery.saleable.dto.StockDTO
import mz.co.commandline.grocery.util.FormatterUtil
import java.math.BigDecimal

class StockAnalysisFragment : BaseFragment() {

    private lateinit var binding: FragmentStockAnalysisBinding

    private lateinit var delegate: InventoryDelegate

    override fun getResourceId(): Int {
        return R.layout.fragment_stock_analysis
    }

    override fun onCreateView() {
        delegate = activity as InventoryDelegate
        val adapter = StockAnalysisAdapter(activity, delegate.stocksDTO)
        adapter.setItemClickListner { stockDTO -> delegate.stockAnalysisDtails(stockDTO as StockDTO) }

        val stockAnalysisRecyclerView = binding.stockAnalysisRecyclerView
        stockAnalysisRecyclerView.adapter = adapter
        stockAnalysisRecyclerView.addItemDecoration((DividerItemDecoration(stockAnalysisRecyclerView.context, DividerItemDecoration.VERTICAL)))

        binding.stockAnalysisTotalMissing.text = delegate.stocksDTO.size.toString()
        binding.stockAnalysisTotalCash.text = FormatterUtil.mtFormat(delegate.stocksDTO.map { it.cashDiscrepancy() }.fold(BigDecimal.ZERO, BigDecimal::add))
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentStockAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.item_track)
    }
}