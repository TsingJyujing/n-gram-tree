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
public class NGramModel<T> implements Serializable {
    
    private NGRamNode headNode = null;
    private int maxDepth = 4;
    
    /**
     * 构造函数：构造默认的模型（深度为4）
     */
    public NGramModel(){
        headNode = new NGRamNode<>(null,0);
    }
    
    /**
     * 构造函数：构造指定深度的模型
     * @param maxDepth 指定的深度
     */
    public NGramModel(int maxDepth){
        this.maxDepth = maxDepth;
        headNode = new NGRamNode<>(null,0);
    }
    
    /**
     *  修改深度
     * @param dep 指定的深度
     */
    public void setMaxDepth(int dep){
        this.maxDepth = dep;
    }
    
    /**
     *
     * @return 获取的深度数值
     */
    public int getMaxDepth(){
        return this.maxDepth;
    }
    
    /**
     *
     * @param trainSet 输入的训练集，是训练单元的数组
     */
    public void train(T [] trainSet){
        for (int i = 0; i<trainSet.length;++i){
            int terminateIndex =  i + maxDepth;
            if ( i + maxDepth > trainSet.length ){
                terminateIndex = trainSet.length;
            }
            T [] subSet = Arrays.copyOfRange(trainSet, i,terminateIndex);
            headNode.putKeys(subSet);
        }
    }
    
    /**
     *
     * @param previousKeys 输入的历史记录
     * @return
     */
    public PredictResult<T> predict(T [] previousKeys){
        PredictResult<T> PredictResult = null;
        if ( previousKeys.length> (maxDepth -1) ){
            previousKeys =  Arrays.copyOfRange(previousKeys,
                    previousKeys.length-(maxDepth -1),previousKeys.length);
        }
        for (int i = 0; i<previousKeys.length; i++){
            T [] subSet = Arrays.copyOfRange(previousKeys,i,previousKeys.length);
            PredictResult = headNode.searchKeys(subSet);
            if (PredictResult.valid){
                PredictResult.predictLayer = subSet.length + 1;
                break;
            }
        }
        return PredictResult;
    }
    
    /**
     *
     * @param filename 需要保存的文件名
     * @return 是否成功的创建了文件
     */
    public boolean saveModel(String filename){
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
    public NGramModel loadModel(String filename){
        //使用序列化读取模型，并且使用gzip解压缩
        try {
            FileInputStream fi = new FileInputStream(filename);
            GZIPInputStream gi = new GZIPInputStream(fi);
            ObjectInputStream si;
            si = new ObjectInputStream(gi);
            NGramModel rtn = (NGramModel)si.readObject();
            si.close();
            return rtn;
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error while saving this model.");
            return null;
        }
    }
}
