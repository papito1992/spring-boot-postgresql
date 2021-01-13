package com.alpaka.model.customer;

import com.alpaka.model.representative.Representative;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
    @SequenceGenerator(name = "customer_id_gen", sequenceName = "customer_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private long customerId;

    @NotBlank(message = "username is mandatory")
    @Size(max = 20)
    @NotNull
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "email is mandatory")
    @Size(max = 50)
    @Email
    @NotNull
    @Column(nullable = false)
    private String email;

    @Size(max = 50)
    @Column(nullable = true)
    private String representativeIsbn;

    @Column(nullable = true, columnDefinition = "boolean default false")
    private Boolean hasRepresentation;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @UpdateTimestamp()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;


    public Customer(@NotBlank(message = "username is mandatory") @Size(max = 20) @NotNull String username, @NotBlank(message = "email is mandatory") @Size(max = 50) @Email @NotNull String email, @Size(max = 50) Representative representativeIsbn, Boolean hasRepresentation) {
        this.username = username;
        this.email = email;
        this.representativeIsbn = representativeIsbn.getIsbn();
        this.hasRepresentation = hasRepresentation;
    }

    public Customer(@NotBlank(message = "username is mandatory") @Size(max = 20) @NotNull String username, @NotBlank(message = "email is mandatory") @Size(max = 50) @Email @NotNull String email) {
        this.username = username;
        this.email = email;
    }
}
