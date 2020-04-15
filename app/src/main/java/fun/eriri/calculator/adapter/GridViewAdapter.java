package fun.eriri.calculator.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fun.eriri.calculator.R;
import fun.eriri.calculator.activity.BaseActivity;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;
    private LayoutInflater layoutInflater;
    private TextView textViews ;
    private int i = 0;

    private TextView ac;

    public GridViewAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    public TextView getViewTxt(){
        return textViews;
    }

    public void setTextViews(TextView textViews) {
        this.textViews = textViews;
    }


    public TextView getAc() {
        return ac;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder ;



        if (convertView == null){
            viewholder = new Viewholder();
            convertView = layoutInflater.inflate(R.layout.item,parent,false);
            viewholder.textView = convertView.findViewById(R.id.txt);
            //动态调整数字大小

            ViewGroup.LayoutParams layoutParams = viewholder.textView.getLayoutParams();
            layoutParams.height = (int) (BaseActivity.height *0.109 * BaseActivity.dip);
            viewholder.textView.setLayoutParams(layoutParams);
            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();
        }


        if (position == 0 && i == 0){
            ac = viewholder.textView;
            i++;
        }


        //这里更改自天颜色
        //更改 AC：0
        // x：1 ；
        // %：2 ；
        // ➗：3
        // x ：7；
        //-：11；
        // + ：15；
        //待：16；
        // = ； 19；  等于号换橘色背景，白色字体


//            Log.e("p", "getView: "+position );
            if(position <4 || position ==7 || position ==11 ||position==15||position==16){
                viewholder.textView.setTextColor(context.getResources().getColor(R.color.orange));
            }else if (position == 19){
                viewholder.textView.setTextColor(context.getResources().getColor(android.R.color.white));
                viewholder.textView.setBackgroundResource(R.drawable.shape);
            }
        viewholder.textView.setText(data.get(position));



        return convertView;
    }

    public static class Viewholder {
        private TextView textView;
    }
}
