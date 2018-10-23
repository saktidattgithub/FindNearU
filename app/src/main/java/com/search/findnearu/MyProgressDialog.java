package com.search.findnearu;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Saktidatt on 02-11-2015.
 */
public class MyProgressDialog {

    Activity activity;
    ProgressDialog dialog;
    public MyProgressDialog(Activity ac)
    {
        activity=ac;

    }
public void start_dialog()
{
    dialog=ProgressDialog.show(activity,"","loading...");
}
    public  void stop_dialog()
    {
    if(dialog!=null && dialog.isShowing())
    {
        dialog.dismiss();
    }
    }
}
