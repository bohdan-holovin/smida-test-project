//package com.holovin.smidatestproject.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.UUID;
//
//@Entity
//@Table(name = "reports")
//@Getter
//@Setter
//public class Report {
//
//    @Id
//    @GeneratedValue
//    private UUID id;
//
//    @ManyToOne
//    @JoinColumn(name = "company_id", nullable = false)
//    private Company company;
//
//    @Column(nullable = false)
//    private Timestamp reportDate;
//
//    @Column(nullable = false)
//    private BigDecimal totalRevenue;
//
//    @Column(nullable = false)
//    private BigDecimal netProfit;
//}
