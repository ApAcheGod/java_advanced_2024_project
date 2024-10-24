package ru.otus.java.advanced.PledgeMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.java.advanced.PledgeMonitoring.entity.Examination;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ExaminationRepository extends JpaRepository<Examination, UUID> {

    @Query(nativeQuery = true,
            value = "select * from examination " +
                    "where pledge_id =:pledgeId " +
                    "and examination_status not in :excludedStatuses")
    List<Examination> findAllByPledgeIdAndExcludeStatus(@Param("pledgeId") UUID pledgeId,
                                                        @Param("excludedStatuses") Set<UUID> excludedStatuses);

    @Query(nativeQuery = true,
        value = "select * from examination" +
                "         where " +
                "             pledge_id = :pledgeId " +
                "           and examination_type = :examinationType" +
                "           and examination_status = :examinationStatus" +
                "         order by updated_at desc " +
                "limit 1")
    Optional<Examination> findLastByPledgeIdAndExaminationStatus(@Param("pledgeId") UUID pledgeId,
                                                                 @Param("examinationType") UUID examinationType,
                                                                 @Param("examinationStatus") UUID examinationStatus);

    List<Examination> findAllByContractId(UUID contractId);

    @Query(nativeQuery = true,
            value = "select distinct ex.contract_id from examination ex " +
            "    left join task_log t on ex.contract_id = t.contract_id " +
            "where ex.examination_status = '1730e0bf-00e9-4052-8fdf-065368eab3c5' " +
            "and t.id is null")
    List<UUID> findAllContractForCreateTask();

}
