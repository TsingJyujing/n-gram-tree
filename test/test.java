import com.sq.tsingjyujing.ngtree.*;
/**
 *
 * @author yuanyifan
 * 测试代码用来测试是否正常工作
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String train_sen = "Kitty want 去 吃 鱼 but I want 去 take some 牛肉 I want 去 eat 牛肉";
        //中文夹杂测试
        String [] test_sen = {
            "Kitty want to 吃 鱼 but I",
            "want to fuck",
            "Kitty want 去",
            "I want 去",
            "want 去",
            "take some",
            "fuck some",
        };
        ngmodel ngm_src = new ngmodel();
        ngm_src.train(train_sen, " ");

        //序列化测试
        String fn = "data.ser";
        ngm_src.save_model(fn);
        ngmodel ngm = new ngmodel().load_model(fn);
        
        //预测测试
        for (String sub_test_sen : test_sen) {
            predict_result pdt = ngm.predict(sub_test_sen.split(" "));
            pdt.display();
        }
    }
    
}
