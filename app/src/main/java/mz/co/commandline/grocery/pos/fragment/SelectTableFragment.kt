package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.adapter.MenuAdapter
import mz.co.commandline.grocery.databinding.FragmentSelectTableBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.menu.MenuItem
import mz.co.commandline.grocery.pos.delegate.PosDelegate


class SelectTableFragment : BaseFragment(), ClickListner<MenuItem> {

    private var _binding: FragmentSelectTableBinding? = null
    private val binding get() = _binding!!

    private lateinit var delegate: PosDelegate

    override fun getResourceId(): Int {
        return R.layout.fragment_select_table
    }

    override fun onCreateView() {

        delegate = activity as PosDelegate

        val openedTables = delegate.getTables()
        val table = delegate.getTable();

        val availableTables = mutableListOf<MenuItem>()

        for (tableNumber in 1..table.unitDTO.numberOfTables) {
            val menuItem = MenuItem(R.string.pos, R.mipmap.ic_table)
            menuItem.number = tableNumber

            if (!openedTables.any { it.tableNumber == tableNumber }) {
                availableTables.add(menuItem)
            }
        }

        val adapter = MenuAdapter(activity, availableTables)
        adapter.setItemClickListner(this)
        binding.fragmentSelectTableRecycleview.adapter = adapter
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentSelectTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.pos_available_tables)
    }

    override fun onClickListner(selectedTable: MenuItem?) {
        delegate.selectedTableNumber(selectedTable!!.number)
    }
}