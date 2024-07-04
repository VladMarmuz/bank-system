package com.banksystem.loan.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan {

    public Loan(String title, String description, Long userId, List<PaymentPeriod> paymentPeriods) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.paymentPeriods = paymentPeriods;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "loan_id", nullable = false)
    private List<PaymentPeriod> paymentPeriods;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loan loan = (Loan) o;

        if (!title.equals(loan.title)) return false;
        if (!description.equals(loan.description)) return false;
        if (!userId.equals(loan.userId)) return false;
        return paymentPeriods.equals(loan.paymentPeriods);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + paymentPeriods.hashCode();
        return result;
    }

    @Override
    public String
    toString() {
        return "Loan [" +
                "id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", userId=" + userId +
                ", paymentPeriods=" + paymentPeriods +
                ']';
    }
}
