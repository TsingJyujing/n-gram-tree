/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sq.tsingjyujing.ngtree;

/**
 * @author yuanyifan
 * Matlab患者的福音，方便在Matlab中的使用
 */
public class MatlabTool {
    /**
     * 显示使用方法
     */
    public void useage() {
        System.out.println("USEAGE:");
        System.out.println("\tmlt = com.sq.tsingjyujing.ngtree.MatlabTool();");
        System.out.println("\tmlt.get_YourType_model();");
        System.out.println("For example:");
        System.out.println("\tmlt.getStringModel();");
        System.out.println("Support types:");
        System.out.println("\tInteger,Integer_Array,");
        System.out.println("\tString,String_Array,");
        System.out.println("\tDouble,Double_Array.");
    }

    /**
     * 显示使用方法
     */
    public void display() {
        this.useage();
    }

    public NGramModel<Integer> getIntegerModel() {
        return new NGramModel<>();
    }

    public NGramModel<Integer[]> getIntegerArrayModel() {
        return new NGramModel<>();
    }

    public NGramModel<int[]> getIntArrayModel() {
        return new NGramModel<>();
    }

    public NGramModel<String> getStringModel() {
        return new NGramModel<>();
    }

    public NGramModel<String[]> getStringArrayModel() {
        return new NGramModel<>();
    }

    public NGramModel<Double> getDoubleModel() {
        return new NGramModel<>();
    }

    public NGramModel<double[]> getDoubleArrayModel() {
        return new NGramModel<>();
    }

}
