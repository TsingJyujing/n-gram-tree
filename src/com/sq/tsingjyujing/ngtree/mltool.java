/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sq.tsingjyujing.ngtree;

/**
 *
 * @author yuanyifan
 * Matlab患者的福音，方便在Matlab中的使用
 */
public class mltool {
    public void useage(){
        System.out.println("USEAGE:");
        System.out.println("\tmlt = com.sq.tsingjyujing.ngtree.mltool();");
        System.out.println("\tmlt.get_YourType_model();");
        System.out.println("For example:");
        System.out.println("\tmlt.get_String_model();");
        System.out.println("Support types:");
        System.out.println("\tInteger,Integer_Array,");
        System.out.println("\tString,String_Array,");
        System.out.println("\tDouble,Double_Array.");
    }
    
    public ngmodel<Integer> get_Integer_model(){
        return new ngmodel<>();
    }
    
    public ngmodel<Integer []> get_Integer_Array_model(){
        return new ngmodel<>();
    }
    
    public ngmodel<int []> get_int_Array_model(){
        return new ngmodel<>();
    }
    
    public ngmodel<String> get_String_model(){
        return new ngmodel<>();
    }
    public ngmodel<String []> get_String_Array_model(){
        return new ngmodel<>();
    }
    public ngmodel<Double> get_Double_model(){
        return new ngmodel<>();
    }
    public ngmodel<Double []> get_Double_Array_model(){
        return new ngmodel<>();
    }
    public ngmodel<double []> get_double_Array_model(){
        return new ngmodel<>();
    }
    
}
