package mz.co.commandline.grocery.util.alert

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import mz.co.commandline.grocery.databinding.SaleTypeBinding
import mz.co.commandline.grocery.sale.dto.SaleType

class SaleTypeDialog(context: Context) : DialogManager<SaleType>(context) {

    override fun dialog(listner: DialogListner<SaleType>?) {
        var binding: SaleTypeBinding = SaleTypeBinding.inflate(LayoutInflater.from(getContext()))
        var builder: AlertDialog.Builder = AlertDialog.Builder(getContext()).setView(binding.root).setCancelable(false)
        dialog = builder.create()
        dialog.show()

        var saleType: SaleType = SaleType.CASH

        binding.saleTypeDirect.setOnClickListener {
            binding.saleTypeDirect.setTextColor(Color.WHITE)
            binding.saleTypePart.setTextColor(Color.BLACK)
            saleType = SaleType.CASH
        }

        binding.saleTypePart.setOnClickListener {
            binding.saleTypePart.setTextColor(Color.WHITE)
            binding.saleTypeDirect.setTextColor(Color.BLACK)
            saleType = SaleType.INSTALLMENT
        }

        binding.saleDialogCancelBtn.setOnClickListener { dialog.dismiss() }

        binding.saleDialogOkBtn.setOnClickListener {
            dialog.dismiss()
            listner?.perform(saleType)
        }
    }
}