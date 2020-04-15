package fun.eriri.calculator.biz;

import android.util.Log;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * 波兰式计算算法类
 * */
public class Calculate {
    private static final Stack<String> operator =  new Stack<>() ;    //操作符
    private static final Stack<String> requation =  new Stack<>();     // 逆波兰式
    private static final Stack<String> answerStact = new Stack<>(); // 逆放 逆波兰式的栈
    private static final Stack<String> equalAnswer = new Stack<>(); // 存放答案的栈
    private String equation;   // 波兰式
    public static final Map<String,Integer> level = new Hashtable<>();

    StringBuilder last = new StringBuilder();

    public Calculate(){
        level.put("+",0);
        level.put("-",0);
        level.put("X",1);
        level.put("÷",1);
        level.put("#",-1);
        level.put("(",-1);
        level.put(")",-1);
        level.put("%",-1);
    }

    public Calculate(String string){
        this();
        equation = string;
    }

    public void setEquation(String equation) {
        Log.e("tpg", "setEquation: "+ equation);
        this.equation = equation;


        last.delete(0,last.length());
    }
    /**
     * 波兰式转逆波兰式
     * */

    //获得逆波兰式
    private Stack<String> getReverse() throws Exception {
        if (equation == null){
            throw new Exception("算式不能为空！");
        }else{
            //填充逆波兰式栈
            reverse();
        }
        return requation;
    }
    //填充逆波兰式栈
    private void reverse(){
        for (int i= 0;i<equation.length();i++){
            String c = String.valueOf(equation.charAt(i));
//            //如果是小数点
//            if(c.equals(".")){
//
//            }
            //如果是数字
            if (isNumber(c)){
                last.append(c);
            }else{
                if (last.length() !=0){
                    requation.push(last.toString());
                    last.delete(0,last.length());
                }
                //算式最后为#，则弹出栈内所有符号放入逆波兰式栈
                if (c.equals("#")){
                    while (!operator.empty()){
                        requation.push(operator.pop());
                    }
                    continue;
                }

                //如果运算符栈为空，则直接放入。否则进行判断
                if (operator.empty()){
                    operator.push(c);
                }else{
                    //如果为（ 则直接放入
                    if (c.equals("(")||c.equals("%")){
                        operator.push(c);
                        continue;
                    }
                    //如果为 ） 则开始向栈内寻找（，弹出两者之间的所有操作符，并丢弃（
                    if (c.equals(")")){
                        while(!operator.peek().equals("(")){
                            requation.push(operator.pop());
                        }
                        operator.pop();
                        //2+3×(4+3)#
                        continue;
                    }
                    //如果为普通运算符，则判断该运算符与操作符栈栈顶元素的优先级
                    //如果当前字符优先级高，则放入操作符栈
                    //如果栈顶字符优先级高，则弹出栈顶放入逆波兰式栈,重复该步骤直到优先级大于栈顶元素
                    while(!isHigher(c)){
                        requation.push(operator.pop());
                    }
                }
            }
        }
    }
   /**
    * 比较当前元素与栈顶元素的优先级,如果当前高，则直接加入栈顶
    * @param now 当前字符
    * @return true代表当前元素优先级高于栈顶元素
    * */
   private boolean isHigher(String now){
       //2+3×(4+3)#
       // -  23         +*
       // -   23       +*(
       //-   234      +*(+
       // - 2343      +*(+
       // 2343+    +*
       // 2343+*+
        if (operator.empty()){
            operator.push(now);
            return true;
        }
        if (level.get(now) > level.get(operator.peek())){
            operator.push(now);
            return true;
        }else {
            return false;
        }
    }
    /*
    * 判断是否为数字
    * */
    private boolean isNumber(String  character){
        boolean b = level.containsKey(character);
        return !b;
    }



    //计算
    private String js(String a ,String b , String operator){
        float result;
        switch (operator){
            case "+":
                result = Float.parseFloat(b)+Float.parseFloat(a);
                break;
            case "-":
                result = Float.parseFloat(b)-Float.parseFloat(a);
                break;
            case "X":
                result = Float.parseFloat(b)*Float.parseFloat(a);
                break;
            case "÷":
                result = Float.parseFloat(b)/Float.parseFloat(a);
                break;
            default:
                result = (float) 0.00;
                break;
        }
        return String.valueOf(result);
    }
    //程序入口
    public String getAnswer() throws Exception {
        getReverse();
        System.out.println(requation.toString());
        while (!requation.empty()){
            answerStact.push(requation.pop());
        }
        System.out.println(answerStact.toString());
        while(!answerStact.empty()){
            if (isNumber(answerStact.peek())){
                equalAnswer.push(answerStact.pop());
            }else{
                //处理如果为百分号时的情况
                if (answerStact.peek().equals("%")){
                    answerStact.pop();
                    float v = Float.parseFloat(equalAnswer.pop()) / 100;
                    equalAnswer.push(String.valueOf(v));
                }else {
                    String pop = answerStact.pop();
                    equalAnswer.push(js(equalAnswer.pop(), equalAnswer.pop(), pop));
                }
            }
        }
        return equalAnswer.pop();
    }



//    public static void main(String[] args) {
//        Calculate calculate = new Calculate();
//        calculate.setEquation("23.2222335×4#");
//            try {
//                String answer = calculate.getAnswer();
//                System.out.println(answer);
//            } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
