package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.adapter.MenuAdapter
import mz.co.commandline.grocery.databinding.FragmentPosBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.menu.MenuItem
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.sale.dto.SaleDTO


class PosFragment : BaseFragment(), ClickListner<MenuItem> {

    private lateinit var tables: List<SaleDTO>

    private lateinit var delegate: PosDelegate

    private var _binding: FragmentPosBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_pos
    }

    override fun onCreateView() {
        delegate = activity as PosDelegate

        tables = delegate.getTables()

        val menuItems = mutableListOf<MenuItem>()

        tables.forEach { table ->
            val menuItem = MenuItem(R.string.pos, R.mipmap.ic_table)
            menuItem.number = (table.id).toInt()
            menuItems.add(menuItem)
        }

        val adapter = MenuAdapter(activity, menuItems)
        adapter.setItemClickListner(this)

        binding.fragmentPosRecycleview.adapter = adapter

        binding.fragmentPosAddBtn.setOnClickListener { delegate.openTable() }
    }

    override fun getTitle(): String {
        return getString(R.string.sales)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickListner(menuItem: MenuItem?) {
        val table = tables.find { table -> table.id.toInt() == menuItem!!.number }

        delegate.selectedTable(table)
    }
}