package utils;

import main.ArrayVisualizer;

public class Rotations {
    private static Writes Writes;

    public Rotations(ArrayVisualizer arrayVisualizer) {
        Rotations.Writes = arrayVisualizer.getWrites();
    }

    // utility functions
    private static void swapBlocksBackwards(int[] array, int a, int b, int len, double pause, boolean mark, boolean auxwrite) {
        for (int i = 0; i < len; i++) {
            Writes.swap(array, a - i - 1, b - i - 1, pause, mark, auxwrite);
        }
    }

    private static void blockSwap(int[] array, int a, int b, int len, double pause, boolean mark, boolean auxwrite) {
        for (int i = 0; i < len; i++) {
            Writes.swap(array, a + i, b + i, pause, mark, auxwrite);
        }
    }

    private static void shiftForwards(int[] array, int start, int length, double pause, boolean mark, boolean auxwrite) {
        int temp = array[start];
        for(int i = 0; i < length; i++) {
            Writes.write(array, start + i, array[start + i + 1], pause, mark, auxwrite);
        }
        Writes.write(array, start + length, temp, pause, mark, auxwrite);
    }

    private static void shiftBackwards(int[] array, int start, int length, double pause, boolean mark, boolean auxwrite) {    
        int temp = array[start + length];
        for(int i = length; i > 0; i--) {
            Writes.write(array, start + i, array[start + i - 1], pause, mark, auxwrite);
        }
        Writes.write(array, start, temp, pause, mark, auxwrite);
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }


    // rotation algorithms
    public static void griesMills(int[] array, int pos, int lenA, int lenB, double pause, boolean mark, boolean auxwrite) {
        while(lenA != 0 && lenB != 0) {
            if(lenA <= lenB) {
                blockSwap(array, pos, pos + lenA, lenA, pause, mark, auxwrite);
                pos += lenA;
                lenB -= lenA;
            }
            else {
                blockSwap(array, pos + (lenA - lenB), pos + lenA, lenB, pause, mark, auxwrite);
                lenA -= lenB;
            }
        }
    }

    public static void threeReversal(int[] array, int pos, int lenA, int lenB, double pause, boolean mark, boolean auxwrite) {
        Writes.reversal(array, pos, pos + lenA - 1, pause, mark, auxwrite);
        Writes.reversal(array, pos + lenA, pos + lenA + lenB - 1, pause, mark, auxwrite);
        Writes.reversal(array, pos, pos + lenA + lenB - 1, pause, mark, auxwrite);
    }

    public static void juggling(int[] array, int pos, int lenA, int lenB, double pause, boolean mark, boolean auxwrite) {
        int n = lenA + lenB;
        int iters = gcd(lenA, n);
        for (int i = 0; i < iters; i++) {
            int t = array[pos + i];
            int j = i;
            while (true) {
                int k = (j + lenA) % n;
                if (k == i) break;
                Writes.write(array, pos + j, array[pos + k], pause, mark, auxwrite);
                j = k;
            }
            Writes.write(array, pos + j, t, pause, mark, auxwrite);
        }
    }

    public static void holyGriesMills(int[] array, int pos, int lenA, int lenB, double pause, boolean mark, boolean auxwrite) {
        while(lenA > 1 && lenB > 1) {
            while(lenA <= lenB) {
                blockSwap(array, pos, pos + lenA, lenA, pause, mark, auxwrite);
                pos  += lenA;
                lenB -= lenA;
            }
            
            if(lenA <= 1 || lenB <= 1) break;
            
            while(lenA > lenB) {
                swapBlocksBackwards(array, pos + lenA - lenB, pos + lenA, lenB, pause, mark, auxwrite);
                lenA -= lenB;
            }
        }
        
        if(lenA == 1) {
            shiftForwards(array, pos, lenB, pause, mark, auxwrite);
        }
        else if(lenB == 1) {
            shiftBackwards(array, pos, lenA, pause, mark, auxwrite);
        }
    }

    public static void helium(int[] array, int pos, int lenA, int lenB, double pause, boolean mark, boolean auxwrite) {
        while (lenB > 1 && lenA > 1) {
            if (lenB < lenA) {
                blockSwap(array, pos, pos + lenA, lenB, pause, mark, auxwrite);
                pos  += lenB;
                lenA -= lenB;
            } else {
                swapBlocksBackwards(array, pos + lenA, pos + lenA + lenB, lenA, pause, mark, auxwrite);
                lenB -= lenA;
            }
        }
        
        if      (lenB == 1) shiftBackwards(array, pos, lenA, pause, mark, auxwrite);
        else if (lenA == 1) shiftForwards(array, pos, lenB, pause, mark, auxwrite);
    }
}
