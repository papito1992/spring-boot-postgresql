package com.alpaka.model.customerLogs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Transient;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Transient
@NoArgsConstructor
public class CustomerActivity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_logs_id_gen")
    @SequenceGenerator(name = "customer_logs_id_gen", sequenceName = "customer_logs_id_gen")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private int responseStatus;
    private String visitedEndpoint;
    private String actionName;
    private String ipAddress;

    public CustomerActivity(String visitedEndpoint, String actionName, String ipAddress) {
        this.visitedEndpoint = visitedEndpoint;
        this.actionName = actionName;
        this.ipAddress = ipAddress;
    }
}
