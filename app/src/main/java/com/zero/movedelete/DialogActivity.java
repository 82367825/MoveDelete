package com.zero.movedelete;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author linzewu
 * @date 16/9/25
 */
public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        MoveDialog dialog = new MoveDialog(DialogActivity.this);
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        
    }
}
