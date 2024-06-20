package com.banksystem.credit.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "credit_id", nullable = false)
    private List<PaymentPeriod> paymentPeriods;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credit credit = (Credit) o;

        if (!title.equals(credit.title)) return false;
        if (!description.equals(credit.description)) return false;
        if (!userId.equals(credit.userId)) return false;
        return paymentPeriods.equals(credit.paymentPeriods);
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
        return "Credit [" +
                "id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", userId=" + userId +
                ", paymentPeriods=" + paymentPeriods +
                ']';
    }
}
