package fun.eriri.calculator.activity;

import android.app.StatusBarManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;


import android.widget.RelativeLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fun.eriri.calculator.R;



public class BaseActivity extends AppCompatActivity {
    RelativeLayout show ;
    RelativeLayout content;

    public static float height = 0;
    public static float width = 0;
    public  static float dip = 0;
    public static float dpi = 0;
    public static final float mydp = 7/64;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        dip = displayMetrics.density;
        dpi = displayMetrics.densityDpi;

        width = width/dip;
        height = height/dip;




    }

    void init(){
        show = findViewById(R.id.show);
        content = findViewById(R.id.action_container);
    }


}
