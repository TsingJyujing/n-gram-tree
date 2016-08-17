package com.sq.tsingjyujing.ngtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author yuanyifan
 * 这类是用来保存预测的结果的，包括预测层数
 * @param <T>
 */
public class predict_result<T> {
    public long sum_count = 0;//总数
    public long predict_layer = 0;//获取这个结果所用的N-Gram的层数（也就是N）
    public boolean valid = false;//是否有效的获取了结果
    public List<word_prob_unit> words = new ArrayList<>();//储存结果的地方
    
    public predict_result(){}
    public predict_result(long predict_layer,long sum_count){
        this.predict_layer = predict_layer;
        this.sum_count = sum_count;
    }
    
    /**
     *
     * @param word 增加的结果词汇
     * @param count 结果的频数
     */
    public void add(T word, long count){
        words.add(new word_prob_unit(word,count));
        sum_count += count;
    }
    
    /**
     * 针对结果进行排序
     */
    public void sort(){
        Collections.sort(words);
    }
    
    /**
     *
     * @param items
     * 显示预测结果的信息和前items个预测结果（至少一个，最多全部显示）
     */
    public void display(long items){
        if (!valid){
            System.out.println("No predict result.");
            return;
        }
        if(items>words.size()){
            items = words.size();
        }else if(items<=0){
            items = 1;
        }
        System.out.printf("Predict Layer:%d\n",predict_layer);
        for (int i = 0; i<items; i++){
            double percent = words.get(i).count*100;
            percent/=sum_count;
            System.out.print("Predict:" + words.get(i).word + " ");
            System.out.printf("Prob(%d/%d):%f%%\n",
                    words.get(i).count,
                    sum_count,
                    percent);
        }
    }
    
    /**
     * 全部显示
     */
    public void display(){
        display(words.size());
    }
    
    /**
     *
     * @return 返回结果的Key列表
     */
    public T [] get_result_keys(){
        if(!valid){
            return (T[]) (new Object [0]);
        }
        T [] return_val =  (T[]) (new Object [words.size()]);
        for (int i = 0; i<words.size(); i++){
            return_val[i] = (T) words.get(i).word;
        }
        return (T[]) return_val;
    }
    
    /**
     *
     * @return 返回结果的频次列表
     */
    public long [] get_result_count(){
        if(!valid){
            return new long [0];
        }
        long [] return_val = new long[words.size()];
        for (int i = 0; i<words.size(); i++){
            return_val[i] = words.get(i).count;
        }
        return return_val;
    }
    
    public double [] get_result_prob(){
        if(!valid){
            return new double [0];
        }
        double [] return_val = new double[words.size()];
        double sum = 0.0f;
        
        for (int i = 0; i<words.size(); i++){
            sum += (double)words.get(i).count;
        }
        
        for (int i = 0; i<words.size(); i++){
            return_val[i] = ((double)words.get(i).count)/sum;
        }
        
        return return_val;
    }
    /**
     *用来储存每个结果的单元
     * @param <T> 索引类型卧槽要我打几遍啊这破软件
     */
    public class word_prob_unit<T> implements Comparable<word_prob_unit>{
        //这下一语成真真的变成内部类了
        public T word;
        public long count = 0;
        public word_prob_unit(){}
        public word_prob_unit(T word, long count){
            this.word = word;
            this.count = count;
        }
        
        @Override
        public int compareTo(word_prob_unit soe) {
            //实现接口的时候比较和实际是反的，这样可以把权值大的放在上面而不需要FLIP
            if(this.count>soe.count){
                return -1;
            }else if(this.count<soe.count){
                return 1;
            }else{
                return 0;
            }
        }
    }
}
