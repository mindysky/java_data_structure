package com.atguigu.srb.oss.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TestOjects {

    @JsonProperty("system")
    private String system;
    @JsonProperty("itemList")
    private List<Item> itemList;

    @NoArgsConstructor
    @Data
    public static class Item {
        @JsonProperty("itemID")
        private String itemID;
        @JsonProperty("itemDate")
        private Object itemDate;
        @JsonProperty("itemLive")
        private Boolean itemLive;
    }
}
