package com.differentdoors.businesscentral.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class JobPlanningLine {
    @JsonProperty("@odata.etag")
    private String etag;

    private String SystemId;
    private String no;
    private String jobNo;
    private String jobTaskNo;
    private String lineNo;
    private String type;
    private String gacReplenishmentSystem;
    private String gacVendorNo;
    private String locationCode;
    private BigDecimal unitCost;
    private Integer quantity;

    public void setJobNo(String no) {
        this.jobNo = "P" + no;
    }
}
