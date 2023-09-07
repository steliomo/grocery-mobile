package mz.co.commandline.grocery.pos.fragment

import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosAddOrdersBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.generics.listner.LongClickListner
import mz.co.commandline.grocery.pos.adapter.PosSaleItemAdapter
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import mz.co.commandline.grocery.util.FormatterUtil


class PosAddOrdersFragment : BaseFragment(), ClickListner<SaleItemDTO>, LongClickListner<SaleItemDTO> {

    private var _binding: FragmentPosAddOrdersBinding? = null
    private val binding get() = _binding!!

    private var mainMenu: Menu? = null

    private var adapter: PosSaleItemAdapter? = null

    private var selectedItems = mutableListOf<SaleItemDTO>()

    private var table: SaleDTO? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_pos_add_orders
    }

    override fun onCreateView() {

        setHasOptionsMenu(true)
        val delegate = activity as PosDelegate
        table = delegate.getSelectedTable()

        binding.addOrdersCode.text = "#" + table?.id
        binding.addOrdersOwner.text = table?.customerDTO?.name

        updateBill()

        adapter = PosSaleItemAdapter(activity, table?.items!!)
        adapter?.setItemClickListner(this)
        adapter?.setItemLongClickListner(this)

        binding.addOrdersRecyclerView.adapter = adapter
        binding.addOrdersRecyclerView.addItemDecoration(DividerItemDecoration(binding.addOrdersRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.addOrdersSelectBtn.setOnClickListener { delegate.selectItem() }
        binding.addOrdersRegistBtn.setOnClickListener { delegate.registAddedItems() }
    }

    private fun updateBill() {
        binding.addOrdersTotal.text = FormatterUtil.mtFormat(table?.total)
        binding.addOrdersTotalPaid.text = FormatterUtil.mtFormat(table?.totalPaid)
        binding.addOrdersTotalToPay.text = FormatterUtil.mtFormat(table?.totalToPay())
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosAddOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTitle(): String {
        return getString(R.string.add_order)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        mainMenu = menu
        inflater.inflate(R.menu.delete_menu, menu)
        showDeleteMenu(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_menu -> {
                table?.removeAll(selectedItems)
                selectedItems = mutableListOf()
                showDeleteMenu(false)
                updateBill()
                adapter?.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDeleteMenu(show: Boolean) {
        mainMenu?.findItem(R.id.delete_menu)?.isVisible = show
    }

    override fun onClickListner(saleitem: SaleItemDTO?) {

        if (!isDeleteMenuVisible()) {
            return
        }

        if (selectedItems.contains(saleitem)) {
            saleitem?.isSelected = false
            selectedItems.remove(saleitem)

            if (selectedItems.isEmpty()) {
                showDeleteMenu(false)
            }
        } else {
            saleitem?.isSelected = true
            selectedItems.add(saleitem!!)
        }

        adapter?.notifyDataSetChanged()
    }

    override fun onLongClickListner(saleitem: SaleItemDTO?) {

        if (isDeleteMenuVisible()) {
            return
        }

        showDeleteMenu(true)
        saleitem?.isSelected = true
        selectedItems.add(saleitem!!)

        adapter?.notifyDataSetChanged()
    }

    fun isDeleteMenuVisible(): Boolean {
        return mainMenu?.findItem(R.id.delete_menu)?.isVisible!!
    }
}