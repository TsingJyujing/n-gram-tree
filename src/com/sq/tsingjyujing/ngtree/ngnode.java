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
    public T word;
    
    //虽说要谨慎使用String作为键值
    //でも、Who TM cares?
    public HashMap<T,ngnode> sub_nodes_map = new HashMap<>();
    
    public long count;
    
    //构造函数（们）简直像开了个后宫，看参数翻牌子
    public ngnode(){
        count = 0;
    }
    
    public ngnode(T add_word){
        word = add_word;
    }
    
    public ngnode(T add_word, long init_count){
        this.word = add_word;
        this.count = init_count;
    }
    
    //析构函数就不写了，垃圾让GC自己捡吧，Hia~Hia~Hia~
    
    
    public void put_words(T[] words){
        //加入一个（串）词的函数
        ngnode this_node = this;
        this.count++;
        for (int i = 0; i < words.length; ++i){
            //尝试在ThisNode里面查找当前词语
            boolean exist_key = this_node.sub_nodes_map.containsKey(words[i]);
            Integer ID;
            if (!exist_key){
                //找不到就新建一个
                this_node.sub_nodes_map.put(words[i],new ngnode(words[i],0));
            }
            this_node = (ngnode) this_node.sub_nodes_map.get(words[i]);
            this_node.count++;
        }
    }
    
    public predict_result<T> search_words(T[] words){
        ngnode this_node = this;
        predict_result<T> rtnval = new predict_result();
        for (T key_word : words) {
            if (!this_node.sub_nodes_map.containsKey(key_word)) {
                return rtnval;//如果找不到就返回空
            }
            this_node = (ngnode) this_node.sub_nodes_map.get(key_word);
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
