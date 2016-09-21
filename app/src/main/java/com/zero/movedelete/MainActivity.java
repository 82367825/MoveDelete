package com.zero.movedelete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author linzewu
 * @date 16-9-21
 */
public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        MoveRelativeLayout moveRelativeLayout = (MoveRelativeLayout) findViewById(R.id.move_layout);
        moveRelativeLayout.setTargetView(findViewById(R.id.button));
        moveRelativeLayout.setIMoveListener(new MoveRelativeLayout.IMoveListener() {
            @Override
            public void onDelete() {
                Toast.makeText(MainActivity.this, "delete the button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
