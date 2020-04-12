package fun.eriri.calculator.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fun.eriri.calculator.R;
import fun.eriri.calculator.TouchListner;
import fun.eriri.calculator.adapter.GridViewAdapter;




public class CalculatorFragment extends BaseFragment {

    private GridView gridView;
    private String[] dataArr;
    private TextView calculate,answer;
    private  final List<String> data = new ArrayList<>();

    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calculator,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    void init() {
        //初始化控件
        calculate = getView().findViewById(R.id.calculator_calculate);
        answer = getView().findViewById(R.id.calculator_answer);
        calculate.setText("0");
        answer.setVisibility(View.GONE);
        gridView = getView().findViewById(R.id.grid);

        gridView.setNumColumns(4);
        //获取数字
        dataArr = getResources().getStringArray(R.array.data);
        Collections.addAll(data, dataArr);
        //初始化适配器
        final GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(), data);
//        gridView.setVerticalSpacing(100);
//        gridView.setLongClickable(true);
        gridView.setAdapter(gridViewAdapter);

        //震动实现
        final TouchListner onTouchListener = new TouchListner() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            VibrationEffect effect = VibrationEffect.createOneShot(40,10);
                            vibrator.vibrate(effect);
                        }else{
                            vibrator.vibrate(40);
                        }
                        break;
                }
                return false;
            }
        };
        gridView.setOnTouchListener(onTouchListener);
        //点击事件处理
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = "";
                //将点击转换为实际数字
//                Log.e("tag", "onItemClick: "+position );
                if (position>=4&&position<=6){
                    number = String.valueOf(position+3);
                }else if (position>=8&&position<=10){
                    number = String.valueOf(position-4);
                } else if (position>=12&&position<=14) {
                    number = String.valueOf(position-11);
                }
                //更改 AC：0
                // x：1 ；
                // %：2 ；
                // ➗：3
                // x ：7；
                //-：11；
                // + ：15；
                //待：16；
                // = ； 19；  等于号换橘色背景，白色字体
                switch (position){
                    case 0:
                        number = "AC";
                        break;
                    case 1:
                        number = "-2";
                        break;
                    case 2:
                        number = "%";
                        break;
                    case 3:
                        number = "÷";
                        break;
                    case 7:
                        number = "x";
                        break;
                    case 11:
                        number = "-";
                        break;
                    case 15:
                        number = "+";
                        break;
                    case 16:
                        number = "-3";
                        break;
                    case 19:
                        number = "=";
                        break;
                }
//                Log.e("p", "onItemClick: "+textView.getText() );
                //将上一个数字的字体变为原来的样子
                TextView viewTxt = gridViewAdapter.getViewTxt();
                if (viewTxt !=null){
                    viewTxt.setTypeface(Typeface.DEFAULT);
                }
                //当点击时将数字加粗
                TextView text = (TextView) view;
                text.setTypeface(Typeface.DEFAULT_BOLD);
                gridViewAdapter.setTextViews(text);

                //将数字算式显示在展示界面上
                if (!number.equals("")){
                    switch (number){
                        case "-2":
                            if (spannableStringBuilder.length()!=0){
                                spannableStringBuilder.
                                        delete(spannableStringBuilder.length()-1,spannableStringBuilder.length());
                            }
                            break;
                        case "AC":
                            spannableStringBuilder.delete(0,spannableStringBuilder.length());
                            break;
                            //这里输出结果
                        default:
                            spannableStringBuilder.append(number);
                            answer.setText("="+getCalculateNumber(spannableStringBuilder.toString()));
                            break;
                    }
                }
                calculate.setText(spannableStringBuilder);

                //最后判断有没有算式来决定是ac还是c 以及 回到初始版面
                if (spannableStringBuilder.length()==0){
                    data.remove(0);
                    data.add(0,"AC");
                    gridViewAdapter.notifyDataSetChanged();
                    answer.setVisibility(View.GONE);
                    calculate.setText("0");
                }else if (spannableStringBuilder.length()==1){
                    data.remove(0);
                    data.add(0,"C");
                    gridViewAdapter.notifyDataSetChanged();
                    answer.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    //计算的方法
    String getCalculateNumber(String str){
        String an = "";
        return an;
    }
}
