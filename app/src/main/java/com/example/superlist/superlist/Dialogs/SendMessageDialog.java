package com.example.superlist.superlist.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.superlist.R;
import com.example.superlist.superlist.Firebase.DataManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SendMessageDialog {

    private TextView dialog_TXT_title;
    private TextInputLayout dialog_EDT_message;
    private Button dialog_BTN_send;
    private final DataManager dataManager = DataManager.getInstance();


    public void show(Activity activity, ArrayList<String> sharedUsers) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_send_messgae);

        findViews(dialog);

        dialog_BTN_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(sharedUsers);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void sendMessage(ArrayList<String> sharedUsers) {

        String message = dialog_EDT_message.getEditText().getText().toString();
        dataManager.sendMessage(message, sharedUsers);
    }

    public void findViews(Dialog dialog) {
        dialog_TXT_title = dialog.findViewById(R.id.dialog_TXT_title);
        dialog_EDT_message = dialog.findViewById(R.id.dialog_EDT_message);
        dialog_BTN_send = dialog.findViewById(R.id.dialog_BTN_send);
    }
}
