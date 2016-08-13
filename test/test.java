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
        String train_sen = "Kitty want to eat fish but I want to take some beef I want to eat beef";
        String [] test_sen = {
            "Kitty want to eat fish but I",
            "want to fuck",
            "Kitty want to",
            "I want 去",
            "want to",
            "take some",
            "fuck some",
        };
        ngmodel ngm_src = new ngmodel(4);
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
