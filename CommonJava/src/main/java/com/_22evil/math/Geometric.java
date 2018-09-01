package com._22evil.math;

/**
 * 几何
 */
public class Geometric {

    /**
     * 计算三角形面积
     */
    public static double getTriangleArea(double a, double b, double  c) {
        // 海伦公式 s*s = p(p-a)(p-b)(p-c)，其中s是面积，p是半周长
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}