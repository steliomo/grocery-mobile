package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentOpenTableDetailsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate

class OpenTableDetailsFragment : BaseFragment() {

    private var _binding: FragmentOpenTableDetailsBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_open_table_details
    }

    override fun onCreateView() {
        val delegate = activity as PosDelegate
        val table = delegate.getTable()

        binding.openTableDetailsTableNumber.text = "#" + table.tableNumber.toString().padStart(2, '0')
        binding.openTableDetailsCustomer.text = table.customerDTO.name
        binding.openTableDetailsCustomerContact.text = table.customerDTO.contact

        binding.openTableDetailsOpenBtn.setOnClickListener { delegate.processOpenTable(table) }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentOpenTableDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.pos_table_details)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}