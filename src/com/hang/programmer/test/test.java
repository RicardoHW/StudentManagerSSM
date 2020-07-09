package com.hang.programmer.test;

import org.junit.Test;

import java.util.Calendar;
import java.util.UUID;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 9:34
 * @Description:
 */

public class test {
    int count=000;

    @Test
    public void test() {
        Calendar today = Calendar.getInstance();
        String str =""+today.get(Calendar.YEAR)+ (today.get(Calendar.MONTH)+1)+today.get(Calendar.DAY_OF_MONTH);
        count++;
        String s = ""+count;
        if (s.length()==1){
            s="00"+s;
        }else if (s.length()==2){
            s = "0" + s;
        }
        String a = str +s;
        System.out.println(a);
    }
}
