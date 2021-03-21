package com.alpaka.model.customerLogs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "CustomerLogs")
@Table(name = "customer_logs", uniqueConstraints = {
//        @UniqueConstraint(columnNames = "username")
})
@NoArgsConstructor
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class CustomerLogs implements Serializable {
    //    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_logs_id_gen")
    @SequenceGenerator(name = "customer_logs_id_gen", sequenceName = "customer_logs_id_gen")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "username", updatable = false, nullable = false)
    private String username;

    @Column(name = "customerActivity")
    private String customerActivity;

    @Column(name = "responseStatus")
    private int responseStatus;

    @Column(name = "visitedEndpoint")
    private String visitedEndpoint;

    @Column(name = "actionName")
    private String actionName;

    @Column(name = "ipAddress")
    private String ipAddress;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
}
