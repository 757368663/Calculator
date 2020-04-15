package fun.eriri.calculator.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fun.eriri.calculator.R;
import fun.eriri.calculator.TouchListner;
import fun.eriri.calculator.adapter.GridViewAdapter;
import fun.eriri.calculator.biz.Calculate;
import fun.eriri.calculator.biz.Util;


public class CalculatorFragment extends BaseFragment {

    private GridView gridView;
    private String[] dataArr;
    private TextView calculate,answer;
    private  final List<String> data = new ArrayList<>();
    private boolean isAnswer = false;

    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    Calculate calculateBiz = new Calculate();

    Typeface thin = Typeface.create("sans-serif-thin",Typeface.NORMAL);
    Typeface light = Typeface.create("sans-serif-light",Typeface.NORMAL);

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
                //获取数字
                String number = Util.getNumber(position);
                //将上一个数字的字体变为原来的字体
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
                            //如果为等于则进行切换字体
                        case "=":
                            isAnswer = true;
                            setType(isAnswer);
                            break;
                            //这里输出结果
                        default:
                            if (isAnswer){
                                setType(false);
                                spannableStringBuilder.delete(0,spannableStringBuilder.length());
                                isAnswer = false;
                            }
                            spannableStringBuilder.append(number);
                            if (Calculate.level.containsKey(number)){
                                if (!number .equals("%")){
                                    break;
                                }

                            }
                            calculateBiz.setEquation(spannableStringBuilder.toString()+"#");
                            String answerStr = null;
                            try {
                                answerStr = calculateBiz.getAnswer();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (answerStr!=null&&answerStr.endsWith(".0")){
                                answerStr = answerStr.substring(0,answerStr.length()-2);
                            }
                            CalculatorFragment.this.answer.setText("="+answerStr);
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


   //切换字体 ，  这里最后会使用动画代替
    void setType(boolean IsAnswer){
        if(IsAnswer){
            answer.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            answer.setTypeface(light);
            answer.setTextColor(getContext().getResources().getColor(R.color.blac));

            calculate.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
            calculate.setTypeface(thin);
            calculate.setTextColor(getResources().getColor(R.color.grey));
        }else{
            answer.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
            answer.setTypeface(thin);
            answer.setTextColor(getContext().getResources().getColor(R.color.grey));

            calculate.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            calculate.setTypeface(light);
            calculate.setTextColor(getResources().getColor(R.color.blac));
        }

    }

}
