package com.x_and_l.work;


import com.x_and_l.work.domain.Set;
import com.x_and_l.work.util.SetUtil;

import java.io.*;
import java.util.Scanner;

/*
* 程序运行入口类
* */
public class Main implements MainInterface{

    private static Main myMain = new Main();
    private final static Scanner sc = new Scanner(System.in);
    private static String op; // 操作数
    private static int realOp; // op - realOp
    private static Set a ; //集合A
    private static Set b ; // 集合B
    private static char isSave;//是否保存计算结果并开始运算？
    private static Boolean flag = false; //是否进行过运算
    private static int count = 0; // 操作进行的次数
    // 反序列化 --
    static {
        try {
            File file = new File("D:\\work\\src\\com\\x_and_l\\work\\setData.txt");
            // 文件不为空
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            if (file.length()!=0) {
                Set o = (Set) inputStream.readObject();
                if (o != null) {
                    a = o;
                    flag = true;
                }
            }
            inputStream.close();
        }catch (Exception e){
            System.out.println("读取文件出现异常");
        }
    }


    public static void main(String[] args) {


        realOperation(); // 启动

        }

    private static void operation(){
        for (int i = 0;i < 20;i++ ){
            System.out.print("*");
        }
        System.out.print("请选择操作");
        for (int i = 0;i < 20;i++ ){
            System.out.print("*");
        }
        System.out.println();
        System.out.println("1.求交集                2.求并集              ");
        System.out.println("3.求差集                4.求补集              ");
        System.out.println("5.字符串是否在集合的判断                      6.退出程序               ");
        System.out.println("********************************************************");
        System.out.print("提示(hint):按1 选择求交集         按2 选择求并集");
        System.out.println("          按3 选择求差集            按4 选择求补集            按5 字符串的判断               \n按6 退出程序");
        System.out.println("请输入你的选择:");

    }


    // 差，并，交 ,补共用方法
    private static void union() {
        b = null;

            if (flag && a != null) {
                while (true) {
                    System.out.println("是否使用上一次的计算结果（y/n）");
                    String input = sc.nextLine();
                    if (input.length() != 1)
                        continue;
                        isSave = input.charAt(0);
                    if (isSave == 'y' || isSave == 'Y') {
                        System.out.println("请输入集合B:");
                        String s = sc.nextLine();
                        b = new Set();
                        myMain.splitStringToSet(s, b);
                    } else if (isSave == 'N' || isSave == 'n') {
                        a = null;
                        if (realOp != 4) {
                            System.out.println("请输入集合A -- 回车代表输入结束:");
                        } else {
                            System.out.println("请输入您选择的全集u -- 回车代表输入结束:");
                        }
                        init();
                    } else {
                        continue;
                    }
                    break;
                }
            }
            else {
                a = null;
                if (realOp != 4) {
                    System.out.println("请输入集合A -- 回车代表输入结束:");
                } else {
                    System.out.println("请输入您选择的全集u -- 回车代表输入结束:");
                }
                init();
            }
        }




    // 初始化集合
    private static void init(){
        String s = sc.nextLine();
        a = new Set();
        myMain.splitStringToSet(s,a);
        System.out.println("请输入集合B:");
        String s2 = sc.nextLine();
        b = new Set();
        myMain.splitStringToSet(s2,b);
    }
    // 操作执行结果
    private static void realOperation() {
        // 欢迎字符串
        String headOrTail = "---------------------------------";
        // 控制台界面初始化
        flag: while (true) {
            System.out.println(headOrTail + "欢迎来到集合运算操作程序" + headOrTail);
            // 操作界面
            operation();
           op = sc.nextLine();
           if (op.length()!=1){
               System.out.println("输入不合法,请重试!!!");
               continue ;
           }

           char q = op.charAt(0);
           if (q>='0'&&q<='9'){
              realOp = Integer.parseInt(q+"");
           }else{
               System.out.println("输入不合法。");
               continue ;
           }
        switch (realOp) {
            case 1:
                union();
                printAllCollectionMessage();
                System.out.println("A∩B:");
                a = SetUtil.intersection(a,b);
                if (a != null)
                System.out.println(a.toString());
                else
                    System.out.println("为空");
                flag = true;
                count++;
                break;
            case 2:
                union();
                printAllCollectionMessage();
                System.out.println("AUB:");
                a = SetUtil.union(a,b);
                if (a != null)
                System.out.println(a.toString());
                else
                System.out.println("为空");
                flag = true;
                count++;
                break;
            case 3:
                union();
                printAllCollectionMessage();
                System.out.println("A-B:");
                a = SetUtil.differentSet(a,b);
                if (a != null)
                System.out.println(a.toString());
                flag = true;
                count++;
                break;
            case 4:
                union();
                printAllCollectionMessage();
                System.out.println("集合B的补集为:");
                a = SetUtil.complement(b, a);
                count++;
                if (a == null){
                    System.out.println("您输入的集合B不是全集的子集！！！");

                }else {
                    System.out.println(a.toString());
                    flag = true;
                }
                break;
            case 5:
                a = new Set();
                System.out.println("请输入用于测试的集合A:");
                String s = sc.nextLine();
                myMain.splitStringToSet(s,a);
                System.out.println("请输入要判断的字符串test");
                String test = sc.nextLine();
                if (isBelongCollection(a,test)) {
                    System.out.println("test在集合A中");
                }else {
                    System.out.println("test不在集合A中");
                }
                    break ;
            case 6:
                if (a != null&&count !=0)
                isSaveResToFile();
                sc.close();
                break flag;
            default:
                System.out.println("没有该选项，请重试:");
                break;
}
        }
    }


    // 保存最后一次计算结果到文件  yes | no
    private static void isSaveResToFile() {
        File file = new File("D:\\work\\src\\com\\x_and_l\\work\\setData.txt");
        while (true) {
            System.out.println("是否保存本次计算结果并退出?（y/n）");

            String input = sc.nextLine();
            if (input.length() != 1)
                continue;
            isSave = input.charAt(0);
            if (isSave == 'y' || isSave == 'Y') {
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                    outputStream.writeObject(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (isSave == 'n' || isSave == 'N') {
                if (file.exists()){
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("文件创建失败");
                    e.printStackTrace();
                }

            }else{
                continue;
            }
            break;
        }
    }


    // 打印集合的值
    private static void printAllCollectionMessage(){
        System.out.println("A集合:");
        if (a != null)
        System.out.println(a.toString());
        else
            System.out.println("为空");
        System.out.println("B集合:");
        if (b != null)
        System.out.println(b.toString());
        else
            System.out.println("为空");
    }


    // 元素是否属于某集合的判定
    private static Boolean isBelongCollection(Set test,String target){{
        return test.isHaving(target);
    }

    }
}
