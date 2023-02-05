package mz.co.commandline.grocery.util.alert

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import mz.co.commandline.grocery.sale.dto.SaleType
import java.util.*

abstract class DialogManager<T>(private val context: Context) {

    protected lateinit var dialog: AlertDialog

    open fun dialog(type: AlertType, message: String, listner: AlertListner) {
        Log.e("ALERT", "Alert Dialog to implement");
    }

    open fun dialog(listner: DialogListner<T>?) {
        Log.e("SALE_TYPE", "Sale Type Dialog to implement");
    }

    open fun dialog(optionName: String, iconId: Int, listner: DialogListner<T>?) {
        Log.e("SALE_TYPE", "Sale Type Dialog to implement");
    }

    fun getContext(): Context {
        return context;
    }
}