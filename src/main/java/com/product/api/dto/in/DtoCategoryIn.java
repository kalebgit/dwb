package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


public class DtoCategoryIn {

    @JsonProperty("category")
    @NotNull
    private String category;

    @JsonProperty("tag")
    @NotNull
    private String tag;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
