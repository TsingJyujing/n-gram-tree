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
        mltool mlt = new mltool();
        
        String train_sen = 
                "Kitty want to eat fish but "
                + "I want to take some beef I want to eat beef";
        String [] test_sen = {
            "Kitty want to eat fish but I",
            "want to fuck",
            "Kitty want to",
            "I want to",
            "want to",
            "take some",
            "fuck some",
        };
        ngmodel<String> ngm_src = mlt.get_String_model();
        ngm_src.train(train_sen.split(" "));

        //序列化测试
        String fn = "data.ser";
        ngm_src.save_model(fn);
        ngmodel<String> ngm = new ngmodel<String>().load_model(fn);
        
        //预测测试
        for (String sub_test_sen : test_sen) {
            predict_result<String> pdt = ngm.predict(sub_test_sen.split(" "));
            pdt.display();
        }
        
        Integer [] TrainSet={1,2,3,4,1,2,3,4,1,2,3,4,1,2,3,4};
        Integer [] TestSet ={2,3,4};
        ngmodel<Integer> int_model = mlt.get_Integer_model();
        int_model.train(TrainSet);
        predict_result<Integer> pdt = int_model.predict(TestSet);
        pdt.display();
        
        mlt.useage();
    }
    
}
