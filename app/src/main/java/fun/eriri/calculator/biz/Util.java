package fun.eriri.calculator.biz;

public class Util {

    /*
    * 将position转为number
    * */
    public static String getNumber(int position){
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
                number = "X";
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
            case 17:
                number = "0";
                break;
            case 18:
                number = ".";
                break;
            case 19:
                number = "=";
                break;
        }
        return number;
    }
}
