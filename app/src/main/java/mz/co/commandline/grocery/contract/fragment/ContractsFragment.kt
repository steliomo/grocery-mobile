package mz.co.commandline.grocery.contract.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.contract.adapter.ContractDetailAdapter
import mz.co.commandline.grocery.contract.delegate.ContractDelegate
import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.databinding.FragmentContractsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentDTO


class ContractsFragment : BaseFragment() {

    private lateinit var binging: FragmentContractsBinding

    private lateinit var delegate: ContractDelegate

    override fun getResourceId(): Int {
        return R.layout.fragment_contracts
    }

    override fun onCreateView() {
        delegate = activity as ContractDelegate
        val adapter = ContractDetailAdapter(activity, delegate.getContracts())
        adapter.setItemClickListner { contract -> delegate.paymentDetails(contract as ContractDTO) }

        val contractsRecyclerView = binging.contractsRecyclerView
        contractsRecyclerView.adapter = adapter
        contractsRecyclerView.addItemDecoration((DividerItemDecoration(contractsRecyclerView.context, DividerItemDecoration.VERTICAL)))
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binging = FragmentContractsBinding.inflate(inflater, container, false)
        return binging.root
    }

    override fun getTitle(): String {
        return getString(R.string.contracts)
    }
}