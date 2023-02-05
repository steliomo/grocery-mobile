package mz.co.commandline.grocery.util.alert

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import mz.co.commandline.grocery.databinding.OptionDialogBinding

class OptionDialog(context: Context) : DialogManager<Option>(context) {

    override fun dialog(optionName: String, iconId: Int, listner: DialogListner<Option>?) {

        var binding: OptionDialogBinding = OptionDialogBinding.inflate(LayoutInflater.from(getContext()))
        var builder: AlertDialog.Builder = AlertDialog.Builder(getContext()).setView(binding.root).setCancelable(false)
        val dialog = builder.create();
        dialog.show()

        var guideOption: Option = Option.VISUALIZE

        binding.optionTitle.text = optionName
        binding.optionImageView.setImageResource(iconId)

        binding.optionVisualize.setOnClickListener {
            binding.optionVisualize.setTextColor(Color.WHITE)
            binding.optionIssue.setTextColor(Color.BLACK)
            guideOption = Option.VISUALIZE
        }

        binding.optionIssue.setOnClickListener {
            binding.optionIssue.setTextColor(Color.WHITE)
            binding.optionVisualize.setTextColor(Color.BLACK)
            guideOption = Option.ISSUE
        }

        binding.optionDialogCancelBtn.setOnClickListener { dialog.dismiss() }

        binding.optionDialogOkBtn.setOnClickListener {
            dialog.dismiss()
            listner?.perform(guideOption)
        }
    }
}