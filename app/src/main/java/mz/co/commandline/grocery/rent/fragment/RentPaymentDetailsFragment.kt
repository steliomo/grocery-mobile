package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentRentPaymentDetailsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.rent.adapter.RentPaymentAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.util.FormatterUtil

class RentPaymentDetailsFragment : BaseFragment() {

    private var _binding: FragmentRentPaymentDetailsBinding? = null
    private val binding get() = _binding!!

    private var delegate: RentDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_rent_payment_details
    }

    override fun onCreateView() {
        delegate = activity as RentDelegate

        val rent = delegate?.rent
        binding.rentPaymentDetailsDate.text = rent?.rentDate
        binding.rentPaymentDetailsEstimated.text = FormatterUtil.mtFormat(rent?.totalEstimated)
        binding.rentPaymentDetailsCalculated.text = FormatterUtil.mtFormat(rent?.totalCalculated)
        binding.rentPaymentDetailsPaid.text = FormatterUtil.mtFormat(rent?.totalPaid)
        binding.rentPaymentDetailsToPay.text = FormatterUtil.mtFormat(rent?.totalToPay)
        binding.rentPaymentDetailsToRefund.text = FormatterUtil.mtFormat(rent?.totalToRefund)

        val rentPaymentDetailsRecyclerView = binding.rentPaymentDetailsRecyclerView
        rentPaymentDetailsRecyclerView.adapter = RentPaymentAdapter(activity, rent?.rentPaymentsDTO!!)
        rentPaymentDetailsRecyclerView.addItemDecoration(DividerItemDecoration(rentPaymentDetailsRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.paymentDetailsBtn.setOnClickListener { delegate?.mainMenu() }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentRentPaymentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        delegate = null
    }

    override fun getTitle(): String {
        return getString(R.string.payment_details)
    }
}