package com.zero.movedelete;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author linzewu
 * @date 16/9/25
 */
public class ListActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        
        final MoveRelativeLayout moveRelativeLayout1 = (MoveRelativeLayout) findViewById(R.id
                .move_layout1);
        moveRelativeLayout1.setIMoveListener(new MoveRelativeLayout.IMoveListener() {
            @Override
            public void onDelete() {
                moveRelativeLayout1.setVisibility(View.GONE);
            }
        });

        final MoveRelativeLayout moveRelativeLayout2 = (MoveRelativeLayout) findViewById(R.id
                .move_layout2);
        moveRelativeLayout2.setIMoveListener(new MoveRelativeLayout.IMoveListener() {
            @Override
            public void onDelete() {
                moveRelativeLayout2.setVisibility(View.GONE);
            }
        });
        final MoveRelativeLayout moveRelativeLayout3 = (MoveRelativeLayout) findViewById(R.id
                .move_layout3);
        moveRelativeLayout3.setIMoveListener(new MoveRelativeLayout.IMoveListener() {
            @Override
            public void onDelete() {
                moveRelativeLayout3.setVisibility(View.GONE);
            }
        });
        final MoveRelativeLayout moveRelativeLayout4 = (MoveRelativeLayout) findViewById(R.id
                .move_layout4);
        moveRelativeLayout4.setIMoveListener(new MoveRelativeLayout.IMoveListener() {
            @Override
            public void onDelete() {
                moveRelativeLayout4.setVisibility(View.GONE);
            }
        });
    }
}
