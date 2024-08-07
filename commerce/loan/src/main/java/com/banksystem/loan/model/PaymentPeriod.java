package com.banksystem.loan.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_period")
public class PaymentPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double sum;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;

    public PaymentPeriod(Double sum, LocalDate startDate, LocalDate endDate, Status status) {
        this.sum = sum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final PaymentPeriod that = (PaymentPeriod) object;

        if (!sum.equals(that.sum)) {
            return false;
        }
        if (!startDate.equals(that.startDate)) {
            return false;
        }
        if (!endDate.equals(that.endDate)) {
            return false;
        }
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = sum.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "PaymentPeriod [" +
                "id=" + id +
                ", sum=" + sum +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ']';
    }
}

