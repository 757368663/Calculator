package fun.eriri.calculator.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

import android.widget.TextView;

import androidx.annotation.Nullable;


@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {

    public static final Typeface thin = Typeface.create("sans-serif-thin",Typeface.NORMAL);
    public static final Typeface light = Typeface.create("sans-serif-light",Typeface.NORMAL);



    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    public void setFont(int isThin){
        switch (isThin){
            case 0:
                setTypeface(Typeface.DEFAULT);
                break;
            case 1:
                setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    public void setAnimation(int  isThin){
        setTextSize(TypedValue.COMPLEX_UNIT_SP,40+isThin);
    }

}
