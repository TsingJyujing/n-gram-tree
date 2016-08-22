package com.sq.tsingjyujing.ngtree;

import java.io.IOException;

import java.io.Serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Arrays;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author yuanyifan
 * @param <T> 所使用的“单词”的类型
 */
public class ngmodel<T> implements Serializable {
    
    private ngnode head_node = null;
    private int max_depth = 4;
    
    /**
     * 构造函数：构造默认的模型（深度为4）
     */
    public ngmodel(){
        head_node = new ngnode<>(null,0);
    }
    
    /**
     * 构造函数：构造指定深度的模型
     * @param max_depth 指定的深度
     */
    public ngmodel(int max_depth){
        this.max_depth = max_depth;
        head_node = new ngnode<>(null,0);
    }
    
    /**
     *  修改深度
     * @param dep 指定的深度
     */
    public void set_max_depth(int dep){
        this.max_depth = dep;
    }
    
    /**
     *
     * @return 获取的深度数值
     */
    public int get_max_depth(){
        return this.max_depth;
    }
    
    /**
     *
     * @param train_set 输入的训练集，是训练单元的数组
     */
    public void train(T [] train_set){
        for (int i = 0; i<train_set.length;++i){
            int terminate_index =  i + max_depth;
            if ( i + max_depth > train_set.length ){
                terminate_index = train_set.length;
            }
            T [] sub_set = Arrays.copyOfRange(train_set, i,terminate_index);
            head_node.put_keys(sub_set);
        }
    }
    
    /**
     *
     * @param previous_keys 输入的历史记录
     * @return
     */
    public predict_result<T> predict(T [] previous_keys){
        predict_result<T> return_value = null;
        if ( previous_keys.length> (max_depth-1) ){
            previous_keys =  Arrays.copyOfRange(previous_keys,
                    previous_keys.length-(max_depth-1),previous_keys.length);
        }
        for (int i = 0; i<previous_keys.length; i++){
            T [] sub_set = Arrays.copyOfRange(previous_keys,i,previous_keys.length);
            return_value = this.head_node.search_keys(sub_set);
            if (return_value.valid){
                return_value.predict_layer = sub_set.length + 1;
                break;
            }
        }
        return return_value;
    }
    
    /**
     *
     * @param filename 需要保存的文件名
     * @return 是否成功的创建了文件
     */
    public boolean save_model(String filename){
        //使用序列化保存模型，并且使用gzip压缩
        try {
            FileOutputStream fo = new FileOutputStream(filename);
            GZIPOutputStream go = new GZIPOutputStream(fo);
            ObjectOutputStream so;
            so = new ObjectOutputStream(go);
            so.writeObject(this);
            so.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error while saving this model.");
            return false;
        } 
    }
    
    /**
     *
     * @param filename 加载模型的文件
     * @return 是否成功的加载了文件
     */
    public ngmodel load_model(String filename){
        //使用序列化读取模型，并且使用gzip解压缩
        try {
            FileInputStream fi = new FileInputStream(filename);
            GZIPInputStream gi = new GZIPInputStream(fi);
            ObjectInputStream si;
            si = new ObjectInputStream(gi);
            ngmodel rtn = (ngmodel)si.readObject();
            si.close();
            return rtn;
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error while saving this model.");
            return null;
        }
    }
}
