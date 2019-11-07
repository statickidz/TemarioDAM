package com.sendacyl.routes.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.sendacyl.R;

public class FilterDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity mAct;
    public Dialog d;
    public Button filter;

    public FilterDialog(Activity activity) {
        super(activity);
        this.mAct = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_filter);
        filter = (Button) findViewById(R.id.btn_yes);
        filter.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.btn_yes:
                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }*/
        dismiss();
    }
}