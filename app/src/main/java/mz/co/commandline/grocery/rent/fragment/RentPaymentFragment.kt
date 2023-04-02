package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentRentPaymentBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO
import mz.co.commandline.grocery.util.DateUtil
import mz.co.commandline.grocery.util.FormatterUtil
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class RentPaymentFragment() : BaseFragment(), View.OnClickListener {

    var _binging: FragmentRentPaymentBinding? = null
    val binding get() = _binging!!

    var _delegate: RentDelegate? = null
    val delegate get() = _delegate!!
    var validators = ArrayList<Validator>()


    override fun getResourceId(): Int {
        return R.layout.fragment_rent_payment
    }

    override fun onCreateView() {
        _delegate = activity as RentDelegate
        val rent = delegate.rent

        binding.rentPaymentDate.text = rent.rentDate
        binding.rentPaymentCustomer.text = rent.customerDTO.name
        binding.rentPaymentEstimated.text = FormatterUtil.mtFormat(rent.totalEstimated)
        binding.rentPaymentCalculated.text = FormatterUtil.mtFormat(rent.totalCalculated)
        binding.rentPaymentPaid.text = FormatterUtil.mtFormat(rent.totalPaid)
        binding.rentPaymentToPay.text = FormatterUtil.mtFormat(rent.totalToPay)
        binding.rentPaymentRefund.text = FormatterUtil.mtFormat(rent.totalToRefund)

        validators.add(UnexpectedValuesValidator(binding.rentPaymentValue, if (rent.totalToPay < rent.totalToRefund) rent.totalToRefund else rent.totalToPay, getString(R.string.payment_value_unexpected)))

        binding.rentPaymentPayBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.payment_regist)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binging = FragmentRentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binging = null
    }

    override fun onClick(button: View?) {
        validators.forEach { validator ->
            if (!validator.isValid) {
                return
            }
        }

        delegate.makePayment(RentPaymentDTO(DateUtil.format(Date(), DateUtil.NORMAL_PATTERN), BigDecimal(TextInputLayoutUtil.getInpuText(binding.rentPaymentValue)), delegate.rent))
    }
}