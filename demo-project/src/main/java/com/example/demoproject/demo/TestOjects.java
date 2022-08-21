package com.example.demoproject.demo;

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

        public String getItemID() {
            return itemID;
        }

        public Object getItemDate() {
            return itemDate;
        }


        public Boolean getItemLive() {
            return itemLive;
        }

    }

    @Override
    public String toString() {
        return "TestOjects{" +
                "system='" + system + '\'' +
                ", itemList=" + itemList +
                '}';
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

}
