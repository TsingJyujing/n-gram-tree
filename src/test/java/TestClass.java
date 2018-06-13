import com.sq.tsingjyujing.ngtree.*;
/**
 *
 * @author yuanyifan
 * 测试代码用来测试是否正常工作
 */
public class TestClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MatlabTool mlt = new MatlabTool();
        
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
        NGramModel<String> ngm_src = mlt.getStringModel();
        ngm_src.train(train_sen.split(" "));

        //序列化测试
        String fn = "data.ser";
        ngm_src.saveModel(fn);
        NGramModel<String> ngm = new NGramModel<String>().loadModel(fn);
        
        //预测测试
        for (String sub_test_sen : test_sen) {
            PredictResult<String> pdt = ngm.predict(sub_test_sen.split(" "));
            pdt.display();
        }
        
        Integer [] TrainSet={1,2,3,4,1,2,3,4,1,2,3,4,1,2,3,4};
        Integer [] TestSet ={2,3,4};
        NGramModel<Integer> intModel = mlt.getIntegerModel();
        intModel.train(TrainSet);
        PredictResult<Integer> pdt = intModel.predict(TestSet);
        pdt.display();
        
        mlt.useage();
    }
    
}
