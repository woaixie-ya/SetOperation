package com.x_and_l.work;

import com.x_and_l.work.domain.Set;

/*
* 提供给集合添加元素的方法
* 样例说明：1   2      4         6  提取的结果为 1,2,4,6
* */
public interface MainInterface {
    default  void splitStringToSet(String target, Set a){
        target = target.trim();
        if (target.equals(""))
            return;
        while (!target.equals("")){
            int len = target.indexOf(" ");
            if (len == -1){
                a.add(target);
                break;
            }
            String ss = target.substring(0,len);
            a.add(ss);
            target = target.substring(len).trim();
        }

    }
}
