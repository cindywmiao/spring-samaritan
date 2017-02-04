package com.coupang.samaritan.web.demo;


import com.coupang.samaritan.web.demo.enumeration.AlarmMethod;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "alarm_strategy")
public class AlarmStrategy {

    // use non business value for primary key
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private AlarmMethod method;

    @Column(name = "receivers")
    private String receivers;
}
