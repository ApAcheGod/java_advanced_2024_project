package ru.otus.java.advanced.dictionaryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.java.advanced.dictionaryservice.entity.Dictionary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {

    Optional<Dictionary> findByCode(String name);

    @Query(nativeQuery = true, value = "select * from dictionary.dictionary " +
            "    where category_id = (" +
            "        select id from dictionary.dictionary " +
            "        where b_code =:categoryBCode)")
    List<Dictionary> findAllByCategoryBCode(@Param("categoryBCode") String categoryBCode);
}
