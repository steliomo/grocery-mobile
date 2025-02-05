package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosBillBinding
import mz.co.commandline.grocery.databinding.FragmentPosCancelBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.adapter.PosSaleItemAdapter
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.util.FormatterUtil


class PosCancelFragment : BaseFragment() {

    private var _binding: FragmentPosCancelBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_pos_cancel
    }

    override fun onCreateView() {
        val delegate = activity as PosDelegate

        val table = delegate.getTable();

        binding.cancelTableCode.text = "#" + table.tableNumber.toString().padStart(2, '0')
        binding.cancelTableOwner.text = table.customerDTO.name
        binding.cancelTableTotal.text = FormatterUtil.mtFormat(table.total)
        binding.cancelTableTotalPaid.text = FormatterUtil.mtFormat(table.totalPaid)
        binding.cancelTableTotalToPay.text = FormatterUtil.mtFormat(table.totalToPay())

        binding.cancelTableRecyclerView.adapter = PosSaleItemAdapter(activity, table.items)
        binding.cancelTableRecyclerView.addItemDecoration(DividerItemDecoration(binding.cancelTableRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.posCancelTableBtn.setOnClickListener { delegate.cancelTable() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosCancelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.cancel_table)
    }
}