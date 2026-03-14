package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


public class DtoCategoryIn {

    @JsonProperty("category")
    @NotNull
    private String category;

    @JsonProperty("tag")
    @NotNull
    private String tag;

}
