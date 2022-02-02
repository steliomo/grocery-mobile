package mz.co.commandline.grocery.inventory.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentStockAnalysisDetailBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate
import mz.co.commandline.grocery.saleable.dto.StockDTO
import mz.co.commandline.grocery.util.DateUtil
import mz.co.commandline.grocery.util.FormatterUtil
import java.math.BigDecimal


class StockAnalysisDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentStockAnalysisDetailBinding

    override fun getResourceId(): Int {
        return R.layout.fragment_stock_analysis_detail
    }

    override fun onCreateView() {
        val delegate = activity as InventoryDelegate
        var stockDTO = delegate.saleableItem as StockDTO

        binding.stockAnalysisDetailProductName.text = stockDTO.productDescriptionDTO.name
        binding.stockAnalysisDetailPurchase.text = FormatterUtil.mtFormat(BigDecimal(stockDTO.purchasePrice))
        binding.stockAnalysisDetailSale.text = FormatterUtil.mtFormat(BigDecimal(stockDTO.salePrice))
        binding.stockAnalysisDetailQuantity.text = stockDTO.quantity
        binding.stockAnalysisDetailMinStock.text = stockDTO.minimumStock

        binding.stockAnalysisDetailLastInventoryDate.text = DateUtil.format(stockDTO.inventoryDate)
        binding.stockAnalysisDetailLastInventoryQuantity.text = stockDTO.inventoryQuantity
        binding.stockAnalysisDetailLastInventoryDiscrepancy.text = stockDTO.discrepancy().toString()

        binding.stockAnalysisDetailLastStockUpdateDate.text = if (stockDTO.stockUpdateDate != null) DateUtil.format(stockDTO.stockUpdateDate) else "NA"
        binding.stockAnalysisDetailLastStockUpdateQuantity.text = if (stockDTO.stockUpdateQuantity != null) stockDTO.stockUpdateQuantity else "NA"
        binding.stockAnalysisDetailLastStockUpdateSales.text = if (stockDTO.sales() != null) stockDTO.sales().toString() else "NA"
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentStockAnalysisDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.stock_details)
    }
}