package mz.co.commandline.grocery.util.alert

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import mz.co.commandline.grocery.databinding.OutputSelectionBinding

class PrinterDialog(context: Context) : DialogManager<Option>(context) {

    override fun dialog(listner: DialogListner<Option>?) {
        var binding: OutputSelectionBinding = OutputSelectionBinding.inflate(LayoutInflater.from(getContext()))
        var builder: AlertDialog.Builder = AlertDialog.Builder(getContext()).setView(binding.root).setCancelable(false)

        val dialog = builder.create();
        dialog.show()

        var option = Option.PRINTER

        binding.outputSelectionPrinterCheck.visibility = View.VISIBLE
        binding.outputSelectionPdfCheck.visibility = View.GONE

        binding.outputSelectionPrinterImageView.setOnClickListener {
            binding.outputSelectionPdfCheck.visibility = View.GONE
            binding.outputSelectionPrinterCheck.visibility = View.VISIBLE
            option = Option.PRINTER
        }

        binding.outputSelectionPdfImageView.setOnClickListener {
            binding.outputSelectionPdfCheck.visibility = View.VISIBLE
            binding.outputSelectionPrinterCheck.visibility = View.GONE
            option = Option.PDF
        }

        binding.outputSelectionCancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        binding.outputSelectionOkBtn.setOnClickListener {
            dialog.dismiss()
            listner?.perform(option)
        }
    }
}