package com.zb.secondary_market.custom;

public class MyRectangle {
    // top, left 左上角那个顶点的坐标
    // width: 宽
    // heigth: 长
    private double top, left, width, height;

    // 构造函数
    public MyRectangle(double top, double left, double width, double height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    // 改变顶点坐标，即改变矩形坐标位置
    public void location(double top, double left) {
        this.top = top;
        this.left = left;
    }

    // 改变宽，高，即改变矩形宽高
    public void size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    // 计算面积，宽×高
    public double area() {
        return width * height;
    }

    // 判断某点是否在矩形内
    public boolean isInside(double x, double y) {
        // 这里采用的是数学上的坐标系，即向上向右为正
        // 如果采用向下向右为正的话，则要改
        // return x > this.left && x < this.left + this.width && y < this.top +
        // this.height && y > this.top;
        // 这里点不包括在边上，如果在边上也算的话，把小于号或大于号改成小于等于及大于等于
        return x > this.left && x < this.left + this.width
                && y > this.top - this.height && y < this.top;
    }
}
