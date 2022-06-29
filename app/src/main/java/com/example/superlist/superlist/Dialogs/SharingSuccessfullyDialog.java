package com.example.superlist.superlist.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.superlist.R;
import com.google.android.material.button.MaterialButton;

public class SharingSuccessfullyDialog {

    private TextView dialog_TXT_title;
    private ImageView dialog_IMG_share;
    private MaterialButton dialog_BTN_ok;



    public void show(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_share_successfully);
        findViews(dialog);
        dialog.show();
    }



    public void findViews(Dialog dialog) {
        dialog_TXT_title = dialog.findViewById(R.id.dialog_TXT_title);
        dialog_IMG_share = dialog.findViewById(R.id.dialog_IMG_share);
        dialog_BTN_ok = dialog.findViewById(R.id.dialog_BTN_ok);
    }
}
