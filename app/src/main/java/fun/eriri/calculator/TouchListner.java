package fun.eriri.calculator;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public abstract class TouchListner implements View.OnTouchListener {

    private int position;

     public TouchListner(){
    }
    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){return position;}


    @Override
    public abstract boolean onTouch(View v, MotionEvent event);


}
