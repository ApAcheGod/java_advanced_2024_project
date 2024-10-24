package ru.otus.java.advanced.pledge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.java.advanced.pledge.entity.Pledge;

import java.util.List;
import java.util.UUID;

public interface PledgeRepository extends JpaRepository<Pledge, UUID> {

    List<Pledge> findAllByContractId(UUID contractId);

    @Query(nativeQuery = true,
            value = "select p.* from pledge p " +
            "    left join sent_log sl on p.id = sl.pledge_id " +
            "where sl.id is null")
    List<Pledge> findAllUnSent();

    @Query(nativeQuery = true,
            value = "select p.* " +
                    "from ( " +
                    "    select *, " +
                    "           row_number() over (partition by pledge_id order by created_at desc) rn " +
                    "    from sent_log " +
                    "    ) subselect " +
                    "join pledge p on p.id = subselect.pledge_id " +
                    "where rn = 1 " +
                    "and subselect.created_at < p.updated_at ")
    List<Pledge> findAllUpdated();
}
