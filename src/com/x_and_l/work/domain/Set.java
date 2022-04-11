package com.x_and_l.work.domain;

/*
*
* 存储数据的集合类
*  存储方式：数组 + 链表   -- 链地址法解决哈希冲突
* */

import com.x_and_l.work.annotation.Operation;
import com.x_and_l.work.util.AlgorithmUtil;
import com.x_and_l.work.util.StringUtil;
import java.io.Serializable;
import java.util.Random;

public class Set implements Serializable {
    // 哈希数组的长度 -- 可变
    public static int length;

    // 真正存放集合元素的变量
    public SetNode[] values;

    // 集合元素个数
    private int sumCount = 0;
    // 将 1-100间最大的质数赋值给length
    static {
        for (int i = 100;i > 0;i--){
            if (AlgorithmUtil.isPrime[i]){
                length = i;
                break;
            }
        }
    }

  // get方法
    public int getSumCount() {
        return sumCount;
    }



    public Set(){
        values = new SetNode[length]; //初始化数组
        // 初始化SetNode中每一个对象
        init();
    }



    private void init(){
        for (int i = 0;i < length;i++){
            values[i] = new SetNode();
        }
    }


    @Operation(value = "获取哈希值")
    private  int getHashCode(String target){

        // 字符串为空 或者 为null
        if (target == null || target.length() == 0){
            return new Random().nextInt(this.length);
        }

        int hashCode = 0;
        for (int i = 0;i<target.length();i++){
            int charValue = (int)target.charAt(i);
            // 自定义哈希函数
            hashCode = ((hashCode << 2) + charValue) ;

        }
        return hashCode % length;
    }


    // 判断集合中是否拥有某个字符串
    public  Boolean isHaving(String target){
        int hashCode = this.getHashCode(target);
        SetNode head = this.values[hashCode];
        if (head.next == null){
            return false;
        }
        while(head.next != null){
            String flag = head.next.node;
            // 判断当前字符串与目标字符串是否相等
            if (StringUtil.isEquals(flag,target)){
                return true;
            }
            head = head.next;
        }
        return false;

    }



    // 往集合添加元素 -- 头插法

    public void add(String target){
        if (sumCount > 10 * length){
            // 扩容 -- 创建下一个集合对象时失效
            extendCapacity();
        }
        // 创建要增加的对象
        SetNode res = new SetNode();
        res.setNode(target);
        // 获取哈希值
        int hashCode = this.getHashCode(target);
        // 获取链表头结点
        SetNode p = values[hashCode];
        if (p.next == null){
            p.next = res;
            sumCount++;
        }
        // 重复元素则不插入
        if (isHaving(target)){
            return;
        }
        else{
            sumCount++; //集合元素个数加一
            res.next = p.next;
            p.next = res;
        }
    }
    // 集合元素过多时，进行values的扩容 -- 下一轮计算时生效
    private void extendCapacity() {
        for (int i = sumCount;i>=0;i--){
            if (AlgorithmUtil.isPrime[i]){
                length = i;
                break;
            }
        }
    }


    // 删除集合中的某个元素
    public void delete(String target){
        if (!this.isHaving(target)){
            return;
        }
        int hashCode = getHashCode(target);
        SetNode node = this.values[hashCode];
        while(node.next != null){
            if (StringUtil.isEquals(node.next.node,target)){
                node.next = node.next.next;
                // 结点没有被引用时，会被gc标记-清除
                sumCount--;
                return;
            }
            node = node.next;
        }
    }

    // -- 用于测试
    public void printfSetLength(int l,int len){
        int[] arr = new int[len];
        for (int i = 0 ; i < len;i++){
            SetNode test = values[i].next;
            while(test != null){
                arr[i]++;
                test = test.next;
            }
        }
        for (int i = l;i<len;i++){
            System.out.println("链表"+i+"的元素个数:" + arr[i]);
        }
    }
    // 打印集合每条链表的长度
    public void realPrintf(){
        if (sumCount < Integer.MAX_VALUE){
            printfSetLength(0,length);
        }else{
            new Thread(()->{
                printfSetLength(0,length / 2);
            }).start();
            printfSetLength(length / 2,length);
        }

    }

    // 重写方法 -- 用于输出集合元素
    @Override
    public String toString() {
            String res = new String();
            for (int i = 0; i < length; i++) {
                SetNode flag = values[i].next;
                while (flag != null) {
                    res += flag.node + " ";
                    flag = flag.next;
                }

            }
            res += "\n";
            return res;

    }
}
