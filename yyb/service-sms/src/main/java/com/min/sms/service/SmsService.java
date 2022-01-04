package com.min.sms.service;

import java.util.HashMap;

public interface SmsService {
    void send(String mobile, String templateCode, HashMap<String, Object> param);
}
