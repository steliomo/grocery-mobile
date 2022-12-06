package mz.co.commandline.grocery.util.alert;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import mz.co.commandline.grocery.R;

public class AlertDialogManager extends DialogManager {

    public AlertDialogManager(Context context) {
        super(context);
    }

    public void dialog(AlertType type, String message, final AlertListner listner) {

        dialog = new AlertDialog.Builder(getContext()).setView(R.layout.alert_dialog)
                .setCancelable(Boolean.FALSE).create();

        dialog.show();

        TextView textview = dialog.findViewById(R.id.alert_dialog_textview);
        Button button = dialog.findViewById(R.id.alert_dialog_btn);
        ImageView imageview = dialog.findViewById(R.id.alert_dialog_imageview);

        switch (type) {
            case SUCCESS:
                imageview.setImageResource(R.mipmap.ic_success);
                break;

            case ERROR:
                imageview.setImageResource(R.mipmap.ic_error);
                break;

            case INFO:
                imageview.setImageResource(R.mipmap.ic_information);
                break;
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
