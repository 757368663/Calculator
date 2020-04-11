package fun.eriri.calculator.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataUtil {

    public static List<String> getData(String[] data){
        List<String> list = new ArrayList<>();

        Collections.addAll(list, data);

        return list;
    }


}
