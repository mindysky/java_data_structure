//package com.min.sms.receiver;
//
//
//import com.min.sms.service.SmsService;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//
//@Component
//@Slf4j
//public class SmsReceiver {
//
//    @Resource
//    private SmsService smsService;
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = MQConst.QUEUE_SMS_ITEM, durable = "true"),
//            exchange = @Exchange(value = MQConst.EXCHANGE_TOPIC_SMS),
//            key = {MQConst.ROUTING_SMS_ITEM}
//    ))
//    public void send(SmsDTO smsDTO){
//        log.info("SmsReceiver消息监听。。。。。。");
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("code", smsDTO.getMessage());
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
////        smsService.send(smsDTO.getMobile(), SmsProperties.TEMPLATE_CODE, param);
//    }
//}
