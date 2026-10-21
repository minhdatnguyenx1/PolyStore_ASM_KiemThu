package com.polystore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportVIP implements Serializable {
    private String fullname;
    private Double totalMoney; // Tổng tiền đã mua
    private Date firstDate;    // Ngày mua đầu tiên
    private Date lastDate;     // Ngày mua sau cùng
}