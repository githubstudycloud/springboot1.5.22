package com.study.testCron.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "data_type_config")
public class DataTypeConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dataType;

    @Column(nullable = false)
    private String refreshUrl;

    @Column(nullable = false)
    private String statusUrl;

    private String additionalParams;
}