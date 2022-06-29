package com.example.superlist.superlist.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.superlist.R;
import com.google.android.material.textview.MaterialTextView;

public class WarningDialog {
    private TextView dialog_TXT_title;
    private MaterialTextView dialog_TXT_message;
    private ImageView dialog_IMG_warning;
    private Button dialog_BTN_ok;

    public void show(Activity activity, String newMessage) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_share_fail);

        findViews(dialog);
        dialog_TXT_message.setText(newMessage);

        dialog_BTN_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void findViews(Dialog dialog) {
        dialog_TXT_title = dialog.findViewById(R.id.dialog_TXT_title);
        dialog_TXT_message = dialog.findViewById(R.id.dialog_TXT_message);
        dialog_IMG_warning = dialog.findViewById(R.id.dialog_IMG_warning);
        dialog_BTN_ok = dialog.findViewById(R.id.dialog_BTN_ok);
    }
}
