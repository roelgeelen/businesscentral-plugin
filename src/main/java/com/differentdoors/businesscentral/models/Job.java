package com.differentdoors.businesscentral.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Job {
    @JsonProperty("@odata.etag")
    private String etag;

    private String no;
    private String description;
    private String customerNo;
    private String typeDeur;
    private String dealAmount;
    private String dealOwner;

    public void setNo(String no) {
        this.no = "P" + no;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = "K" + customerNo;
    }
}
