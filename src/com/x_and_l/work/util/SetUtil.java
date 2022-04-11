package com.x_and_l.work.util;

/*
 *
 * 集合操作的工具类
 *
 * 具体思想：参考哈希（链地址法）的操作方式
 * */

import com.x_and_l.work.annotation.Operation;
import com.x_and_l.work.domain.Set;
import com.x_and_l.work.domain.SetNode;

public class SetUtil {

    @Operation(value = "交")

    public static Set intersection(Set a, Set b){
        if (a == null || b == null){
            return null;
        }
        Set res = new Set();

        for (int i = 0;i<Set.length;i++){
            SetNode head = b.values[i].next;
            while(head != null){
                if (a.isHaving(head.node)) {
                    res.add(head.node);
                }
                head = head.next;
            }

        }
        // 如果交集没有元素 - 返回空
        if (res.getSumCount() == 0)
            return null;

            return res;
    }

    @Operation(value = "并")

    public static Set union(Set a,Set b){
        if (a == null || b == null)
            return null;
        // 算法实现基础 -- A ∪ B = A + B - A ∩ B
        // 把b集合的元素插入a中（交集除外）
        for (int i = 0 ;i < Set.length;i++){
            SetNode flag = b.values[i].next;
            while(flag != null){
                if (!a.isHaving(flag.node)){
                    a.add(flag.node);
                }
                flag = flag.next;
            }
        }
        if (a.getSumCount() == 0)
            return null;
        return a;
    }

    @Operation(value = "补集 u为全集")

    public static Set complement(Set a,Set u){
        if (!isBelongU(a,u)){
            return null; //a 不是 b的子集 则返回空
        }
        return differentSet(u,a);
    }

    @Operation(value = "差集")
    public static Set differentSet(Set a,Set b){
        for (int i = 0;i< Set.length;i++){
            SetNode flag = b.values[i].next;
            while(flag != null) {
                a.delete(flag.node);
                flag = flag.next;
            }
        }
        if (a.getSumCount() == 0)
            return null;
        return a;
    }



   @Operation(value = "子集的判断")
    public static Boolean isBelongU(Set a,Set b){
        if (a == null || b == null){
            return false;
        }
        if (b.getSumCount() < a.getSumCount()){
            return false;
        }
        for (int i = 0;i < Set.length;i++){
            SetNode p = a.values[i].next;
            while(p != null){
                if (!b.isHaving(p.node)){
                    return false;
                }
                p = p.next;
            }
        }
        return true;
    }
}
