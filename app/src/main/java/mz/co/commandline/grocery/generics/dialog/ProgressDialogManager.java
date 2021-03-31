package mz.co.commandline.grocery.generics.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {

    private Context context;

    public ProgressDialogManager(Context context) {
        this.context = context;
    }

    public ProgressDialog getProgressBar(String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(Boolean.FALSE);
        return progressDialog;
    }
}
