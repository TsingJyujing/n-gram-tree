package com.sq.tsingjyujing.ngtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @param <T>
 * @author yuanyifan
 * 这类是用来保存预测的结果的，包括预测层数
 */
public class PredictResult<T> {
    /**
     * 总数
     */
    public long sumCount = 0;
    /**
     * 获取这个结果所用的N-Gram的层数（也就是N）
     */
    public long predictLayer = 0;
    /**
     * 是否有效的获取了结果
     */
    public boolean valid = false;
    /**
     * 储存结果的地方
     */
    public List<KeyProbabilityUnit> keys = new ArrayList<>();

    /**
     * 构造空的结果集
     */
    public PredictResult() {
    }

    /**
     * @param predictLayer 预测使用的层次数（包含退化层次数）
     * @param sumCount     这一节点的子树频次总和
     */
    public PredictResult(long predictLayer, long sumCount) {
        this.predictLayer = predictLayer;
        this.sumCount = sumCount;
    }

    /**
     * @param key   增加的结果词汇
     * @param count 结果的频数
     */
    public void add(T key, long count) {
        keys.add(new KeyProbabilityUnit(key, count));
        sumCount += count;
    }

    /**
     * 针对结果进行排序
     */
    public void sort() {
        Collections.sort(keys);
    }

    /**
     * @param items 显示预测结果的信息和前items个预测结果（至少一个，最多全部显示）
     */
    public void display(long items) {
        if (!valid) {
            System.out.println("No predict result.");
            return;
        }
        if (items > keys.size()) {
            items = keys.size();
        } else if (items <= 0) {
            items = 1;
        }
        System.out.printf("Predict Layer:%d\n", predictLayer);
        for (int i = 0; i < items; i++) {
            double percent = keys.get(i).count * 100;
            percent /= sumCount;
            System.out.print("Predict:" + keys.get(i).key + " ");
            System.out.printf("Prob(%d/%d):%f%%\n",
                    keys.get(i).count,
                    sumCount,
                    percent);
        }
    }

    /**
     * 全部显示
     */
    public void display() {
        display(keys.size());
    }

    /**
     * @return 返回结果的Key列表
     */
    public T[] getResultKeys() {
        if (!valid) {
            return (T[]) (new Object[0]);
        }
        T[] returnVal = (T[]) (new Object[keys.size()]);
        for (int i = 0; i < keys.size(); i++) {
            returnVal[i] = (T) keys.get(i).key;
        }
        return (T[]) returnVal;
    }

    /**
     * @return 返回结果的频次列表
     */
    public long[] getResultCount() {
        if (!valid) {
            return new long[0];
        }
        long[] returnVal = new long[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            returnVal[i] = keys.get(i).count;
        }
        return returnVal;
    }

    /**
     * @return 各个键值的概率
     */
    public double[] getResultProb() {
        if (!valid) {
            return new double[0];
        }
        double[] returnVal = new double[keys.size()];
        double sum = 0.0f;

        for (KeyProbabilityUnit key : keys) {
            sum += (double) key.count;
        }

        for (int i = 0; i < keys.size(); i++) {
            returnVal[i] = ((double) keys.get(i).count) / sum;
        }

        return returnVal;
    }

    /**
     * 用来储存每个结果的单元
     *
     * @param <T> 索引类型卧槽要我打几遍啊这破软件
     */
    public class KeyProbabilityUnit<T> implements Comparable<KeyProbabilityUnit> {
        //这下一语成真真的变成内部类了
        public T key;
        public long count = 0;

        public KeyProbabilityUnit() {
        }

        /**
         * @param key   输入的键值
         * @param count 输入的频次
         */
        public KeyProbabilityUnit(T key, long count) {
            this.key = key;
            this.count = count;
        }

        @Override
        public int compareTo(KeyProbabilityUnit soe) {
            //实现接口的时候比较和实际是反的，这样可以把权值大的放在上面而不需要FLIP
            if (this.count > soe.count) {
                return -1;
            } else if (this.count < soe.count) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
