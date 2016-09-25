package com.zero.movedelete;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * @author linzewu
 * @date 16/9/25
 */
public class MoveDialog extends Dialog {
    
    public MoveDialog(Context context) {
        super(context, R.style.MoveDeleteDialog);
    }

    public MoveDialog(Context context, int themeResId) {
        super(context, R.style.MoveDeleteDialog);
    }

    protected MoveDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, R.style.MoveDeleteDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    }

    @Override
    public void show() {
        super.show();
        showStatusBar();
    }

    @Override
    public void setContentView(int layoutResID) {
        
        MoveRelativeLayout moveRelativeLayout = new MoveRelativeLayout(getContext());
        View view = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        moveRelativeLayout.addView(view);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        super.setContentView(moveRelativeLayout);
        moveRelativeLayout.setIMoveListener(new MoveRelativeLayout.IMoveListener() {
            @Override
            public void onDelete() {
                dismiss();
            }
        });
    }
    
    private void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }
}
