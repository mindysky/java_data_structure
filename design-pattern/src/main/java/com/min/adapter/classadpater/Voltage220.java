package com.min.adapter.classadpater;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.SoapBindingParameterStyle;

public class Voltage220 {
    public int output220V(){
        int src = 220;
        System.out.println("voltage"+src);
        return src;
    }
}
