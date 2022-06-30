package com.example.superlist.superlist.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.superlist.R;
import com.example.superlist.superlist.Firebase.DataManager;


public class GetMessageDialog {

    private TextView dialog_TXT_title;
    private TextView dialog_TXT_message;
    private Button dialog_BTN_ok;


    private final DataManager dataManager = DataManager.getInstance();


    public void show(Activity activity, String userUID, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_get_message);

        findViews(dialog);
        dialog_TXT_message.setText(message);

        dialog_BTN_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataManager.deleteMessageFromDB(userUID);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void findViews(Dialog dialog) {
        dialog_TXT_title = dialog.findViewById(R.id.dialog_TXT_title);
        dialog_TXT_message = dialog.findViewById(R.id.dialog_TXT_message);
        dialog_BTN_ok = dialog.findViewById(R.id.dialog_BTN_ok);
    }
}
