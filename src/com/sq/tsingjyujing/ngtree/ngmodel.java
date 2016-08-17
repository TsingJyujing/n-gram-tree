package com.sq.tsingjyujing.ngtree;

import java.io.Serializable;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 *
 * @author yuanyifan
 * @param <T>
 */
public class ngmodel<T> implements Serializable {
    
    private ngnode head_node = null;
    private int max_depth = 4;
    
    public ngmodel(){
        head_node = new ngnode<>(null,0);
    }
    
    public ngmodel(int max_depth){
        this.max_depth = max_depth;
        head_node = new ngnode<>(null,0);
    }
    
    public void set_max_depth(int dep){
        this.max_depth = dep;
    }
    
    public int get_max_depth(){
        return this.max_depth;
    }
    
    public void train(T [] train_set){
        for (int i = 0; i<train_set.length;++i){
            int terminate_index =  i + max_depth;
            if ( i + max_depth > train_set.length ){
                terminate_index = train_set.length;
            }
            T [] sub_set = Arrays.copyOfRange(train_set, i,terminate_index);
            head_node.put_words(sub_set);
        }
    }
    
    public predict_result<T> predict(T [] sentence_in){
        predict_result<T> return_value = null;
        if ( sentence_in.length> (max_depth-1) ){
            sentence_in =  Arrays.copyOfRange(sentence_in,
                    sentence_in.length-(max_depth-1),sentence_in.length);
        }
        for (int i = 0; i<sentence_in.length; i++){
            T [] sub_set = Arrays.copyOfRange(sentence_in,i,sentence_in.length);
            return_value = this.head_node.search_words(sub_set);
            if (return_value.valid){
                return_value.predict_layer = sub_set.length + 1;
                break;
            }
        }
        return return_value;
    }
    
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
