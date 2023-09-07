package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.adapter.MenuAdapter
import mz.co.commandline.grocery.databinding.FragmentTableDetailsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.menu.MenuItem
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.util.FormatterUtil


class TableDetailsFragment : BaseFragment(), ClickListner<MenuItem> {

    private lateinit var delegate: PosDelegate

    private var _binding: FragmentTableDetailsBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_table_details
    }

    override fun onCreateView() {

        delegate = activity as PosDelegate


        val table = delegate.getSelectedTable()
        binding.tableDetailsCode.text = "#" + table.id
        binding.tableDetailsOwner.text = table.customerDTO.name
        binding.tableDetailsTotal.text = FormatterUtil.mtFormat(table.total)
        binding.tableDetailsTotalPaid.text = FormatterUtil.mtFormat(table.totalPaid)
        binding.tableDetailsTotalToPay.text = FormatterUtil.mtFormat(table.totalToPay())

        val menuItems = listOf(MenuItem(R.string.add_order, R.mipmap.ic_add_order), MenuItem(R.string.payments, R.mipmap.ic_payment), MenuItem(R.string.print_bill, R.mipmap.ic_bill))
        val adapter = MenuAdapter(activity, menuItems)

        adapter.setItemClickListner(this)

        binding.tableDetailsRecycleview.adapter = adapter
    }

    override fun getTitle(): String {
        return getString(R.string.pos_table_details)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentTableDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickListner(menuItem: MenuItem?) {
        delegate.selectedMenuItem(menuItem!!.iconId)
    }
}
