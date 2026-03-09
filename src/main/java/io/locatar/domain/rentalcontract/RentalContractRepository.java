package io.locatar.domain.rentalcontract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.UUID;

public interface RentalContractRepository extends JpaRepository<RentalContractEntity, UUID> {

    long count();

    @Query("SELECT COALESCE(SUM(r.rentValue), 0) FROM RentalContractEntity r")
    BigDecimal sumAllRentValues();
}
