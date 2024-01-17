package org.example;

import jakarta.persistence.*;
import lombok.*;
import org.example.Category;
import org.example.Account;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "account")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime createdAt;

    @Enumerated (value = EnumType.STRING)
    private Category category;

    private double amount;

    private String name;
}
