package mz.co.commandline.grocery.report.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentCostsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.report.adapter.CostAdapter
import mz.co.commandline.grocery.report.delegate.ReportDelegate
import mz.co.commandline.grocery.util.FormatterUtil


class CostsFragment : BaseFragment() {

    private var delegate: ReportDelegate? = null

    private var binding: FragmentCostsBinding? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_costs
    }

    override fun onCreateView() {
        delegate = activity as ReportDelegate

        binding!!.costStartDate.text = delegate!!.startDate
        binding!!.costEndDate.text = delegate!!.endDate
        binding!!.costGrandTotal.text = FormatterUtil.mtFormat(delegate!!.totalExpense)

        val costRecyclerView = binding!!.costRecyclerView
        costRecyclerView.adapter = CostAdapter(activity, delegate!!.expensesReport())
        costRecyclerView.addItemDecoration(DividerItemDecoration(costRecyclerView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getTitle(): String {
        return getString(R.string.expenses)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentCostsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}