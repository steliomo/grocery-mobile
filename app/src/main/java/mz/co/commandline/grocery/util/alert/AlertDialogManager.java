package mz.co.commandline.grocery.util.alert;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mz.co.commandline.grocery.R;

public class AlertDialogManager {

    private Context context;

    public AlertDialogManager(Context context) {
        this.context = context;
    }

    public void dialog(AlertType type, String message, final AlertListner listner) {

        final AlertDialog dialog = new AlertDialog.Builder(context).setView(R.layout.alert_dialog)
                .setCancelable(Boolean.FALSE).create();

        dialog.show();

        TextView textview = dialog.findViewById(R.id.alert_dialog_textview);
        Button button = dialog.findViewById(R.id.alert_dialog_btn);

        if (AlertType.ERROR == type) {
            ImageView imageview = dialog.findViewById(R.id.alert_dialog_imageview);
            imageview.setImageResource(R.mipmap.ic_error);
        }

        textview.setText(message);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (listner == null) {
                    return;
                }

                listner.perform();
            }
        });
    }
}
