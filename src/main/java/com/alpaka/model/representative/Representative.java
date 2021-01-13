package com.alpaka.model.representative;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@Entity
@Table(name = "representative",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@RequiredArgsConstructor
public class Representative implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "representative_id_gen")
    @SequenceGenerator(name = "representative_id_gen", sequenceName = "representative_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Size(min = 6, max = 20)
    @NonNull
    private String username;

    @Size(min = 5, max = 50)
    @Email
    @NonNull
    private String email;

    @Column(unique = true)
    @NonNull
    private String isbn;

}
