package com.x_and_l.work.util;

import java.util.Arrays;

/*
* 常见算法
*
* */
public final  class AlgorithmUtil {
  private final static  int length = 3000;  // 所求素数的范围
  public static boolean[] isPrime = new boolean[length + 1];
  // 找到 0 - length的所有素数 --埃拉托色尼筛选法 0(NloglogN)
  static {
      int j;
      Arrays.fill(isPrime,true);
      isPrime[0] = isPrime[1] = false;
      for (int i = 2;i<Math.sqrt(length);i++){
          if (isPrime[i]){
              j = i + i;
              while (j <= length){
                  isPrime[j] = false;
                  j = j + i;
              }
          }
      }
  }


}
