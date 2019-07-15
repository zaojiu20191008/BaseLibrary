package com.niubility.library;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


//    public void bubbleSort(int[] sortArray) {
//
//        boolean isEnd = false;
//        int cycleNum = sortArray.length - 1;
//        int compareNum;
//        int temp;
//        for (int i = 0; i < cycleNum; i++) {
//            compareNum = cycleNum - i;
//            for (int j = 0; j < compareNum; j++) {
//                if (sortArray[j] > sortArray[j+1]) {
//                    temp = sortArray[j];
//                    sortArray[j]  = sortArray[j + 1];
//                    sortArray[j + 1] = temp;
//                    isEnd = false;
//                }
//            }
//            if (isEnd) {
//                break;
//            }
//        }
//
//    }


}