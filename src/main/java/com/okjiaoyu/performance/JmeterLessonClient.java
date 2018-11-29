package com.okjiaoyu.performance;

import com.alibaba.fastjson.JSONObject;
import com.noriental.lessonsvr.entity.request.LongRequest;
import com.noriental.lessonsvr.entity.vo.LinkPublishResource;
import com.noriental.lessonsvr.rservice.LessonService;
import com.noriental.validate.bean.CommonResponse;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-11-29:13:58
 * Modify date: 2018-11-29:13:58
 */
public class JmeterLessonClient extends AbstractJavaSamplerClient {

        @Resource
        private LessonService lessonService;

        private long start = 0;//记录测试开始时间；
        private long end = 0;//记录测试结束时间；
        private String reqId = "2328391112";
        private long id = 100;

        //初始化操作
        @Override
        public void setupTest(JavaSamplerContext arg0) {
            DubboInit init = DubboInit.getInstance();
            init.initContext();
            lessonService = (LessonService) init.getBean("lessonService");
        }
        @Override
        public Arguments getDefaultParameters() {
            Arguments arguments = new Arguments();
            arguments.addArgument("reqId",reqId);
            arguments.addArgument("id",String.valueOf(id));
            return arguments;
        }

        public JmeterLessonClient() {
            super();
        }



        public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
            SampleResult sr = new SampleResult();
            setValues(javaSamplerContext);
            sr.sampleStart();
            start = System.currentTimeMillis();
            LongRequest request = new LongRequest();
            request.setReqId(reqId);
            request.setId(id);
            try {
                CommonResponse<List<LinkPublishResource>> response = lessonService.fetchResourceByPublishId(request);
                if (response !=null && response.getData() !=null){
                    sr.setResponseData(JSONObject.toJSON(response.getData()).toString(),null);
                    sr.setSuccessful(true);
                    sr.setResponseMessage(response.getMessage());
                    sr.setDataType(SampleResult.TEXT);
                }
                else {
                    sr.setSuccessful(false);
                }
            } catch (Exception e) {
            } finally {
                sr.sampleEnd();
            }

            return sr;
        }

        /**
         * 获取jmeter输入的参数值
         *
         * @return
         */
        public void setValues(JavaSamplerContext arg0) {
            reqId = arg0.getParameter("reqId",reqId);
        }


        @Override
        public void teardownTest(JavaSamplerContext context) {
            end = System.currentTimeMillis();
        }

//        public static void main(String[] args) {
//        Arguments arguments = new Arguments();
//        arguments.addArgument("reqId","234235231");
//            arguments.addArgument("id","10001");
//        JavaSamplerContext javaSamplerContex = new JavaSamplerContext(arguments);
//        JmeterLessonClient jMeter = new JmeterLessonClient();
//        jMeter.setupTest(javaSamplerContex);
//        jMeter.runTest(javaSamplerContex);
//    }

}
