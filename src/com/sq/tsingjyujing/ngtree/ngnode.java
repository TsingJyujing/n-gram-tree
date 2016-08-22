/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sq.tsingjyujing.ngtree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author yuanyifan
 * @param <T> 用于索引节点的类型
 * 
 */
public class ngnode<T> implements Serializable {
    //其实完全可以作为ngmodel的一个内部类
    //先这么写着吧
    public T key;
    
    //虽说要谨慎使用String作为键值
    //でも、Who TM cares?
    public HashMap<T,ngnode> sub_nodes_map = new HashMap<>();
    
    public long count = 0;
    
    /**
     * 生成默认的节点
     */
    public ngnode(){
        count = 0;
    }
    
    /**
     *
     * @param add_key 生成节点的键值（“词汇”）
     */
    public ngnode(T add_key){
        key = add_key;
    }
    
    /**
     *
     * @param add_key 添加的键值（“词汇”）
     * @param init_count 键值的初始数值
     */
    public ngnode(T add_key, long init_count){
        this.key = add_key;
        this.count = init_count;
    }
    
    /**
     *
     * @param keys 训练的键值序列
     */
    public void put_keys(T[] keys){
        //加入一个（串）词的函数
        ngnode this_node = this;
        this.count++;
        for (int i = 0; i < keys.length; ++i){
            //尝试在ThisNode里面查找当前词语
            boolean exist_key = this_node.sub_nodes_map.containsKey(keys[i]);
            Integer ID;
            if (!exist_key){
                //找不到就新建一个
                this_node.sub_nodes_map.put(keys[i],new ngnode(keys[i],0));
            }
            this_node = (ngnode) this_node.sub_nodes_map.get(keys[i]);
            this_node.count++;
        }
    }
    
    /**
     *
     * @param keys 输入的历史键值序列
     * @return 预测的结果
     */
    public predict_result<T> search_keys(T[] keys){
        ngnode this_node = this;
        predict_result<T> rtnval = new predict_result();
        for (T sub_key : keys) {
            if (!this_node.sub_nodes_map.containsKey(sub_key)) {
                return rtnval;//如果找不到就返回空
            }
            this_node = (ngnode) this_node.sub_nodes_map.get(sub_key);
        }
        if(this_node.sub_nodes_map.size()>0){
            rtnval.valid = true;
            for (Map.Entry<T,ngnode> entry : 
                    (Set<Map.Entry<T,ngnode>>) 
                    this_node.sub_nodes_map.entrySet()) {
                rtnval.add(entry.getKey(),entry.getValue().count);
            }
            rtnval.sort();
        }
        return rtnval;
    }
}
