package ru.otus.java.advanced.dictionaryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "DICTIONARY", schema = "DICTIONARY")
public class Dictionary {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name = "CATEGORY_ID")
    private UUID categoryId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "B_CODE")
    private String bCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Dictionary user = (Dictionary) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
