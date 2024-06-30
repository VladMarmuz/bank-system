package com.banksystem.credit.repository;

import com.banksystem.credit.model.Credit;
import com.banksystem.overdue.model.OverdueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByUserId(Long userId);

    @Query("""
            SELECT SUM(pp.sum) AS remainingSumma, MIN(pp.startDate) AS overdueDate, cr.userId AS userId
            FROM Credit cr
            JOIN cr.paymentPeriods pp
            WHERE pp.startDate < :date
            GROUP BY cr.userId
            """)
    List<OverdueProjection> findOverdueHql(@Param("date") LocalDate date);


}
