package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosBillBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.adapter.PosSaleItemAdapter
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.util.FormatterUtil


class PosBillFragment : BaseFragment() {

    private var _binding: FragmentPosBillBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_pos_bill
    }

    override fun onCreateView() {

        val delegate = activity as PosDelegate

        val table = delegate.getTable();

        binding.sendBillCode.text = "#" + table.tableNumber.toString().padStart(2, '0')
        binding.sendBillOwner.text = table.customerDTO.name
        binding.sendBillTotal.text = FormatterUtil.mtFormat(table.total)
        binding.sendBillTotalPaid.text = FormatterUtil.mtFormat(table.totalPaid)
        binding.sendBillTotalToPay.text = FormatterUtil.mtFormat(table.totalToPay())

        binding.sendBillRecyclerView.adapter = PosSaleItemAdapter(activity, table.items)
        binding.sendBillRecyclerView.addItemDecoration(DividerItemDecoration(binding.sendBillRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.sendBillPrintImageView.setOnClickListener { delegate.print() }
        binding.sendBillWhatsAppImageView.setOnClickListener { delegate.sendToWhatsApp() }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.print_bill)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}