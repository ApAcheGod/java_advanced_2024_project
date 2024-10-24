-- drop schema if exists pledge_monitoring cascade;
-- create schema pledge_monitoring;
create table if not exists monitoring_pattern
(
    id                               uuid
        constraint pk_monitoring_pattern_id
            primary key,
    pattern                          text,
    price_first_monitoring_period    numeric,
    price_base_monitoring_period     numeric,
    document_first_monitoring_period numeric,
    document_base_monitoring_period  numeric
);


create table if not exists examination
(
    id                    uuid
        constraint pk_examination_id
            primary key,
    contract_id           uuid,
    pledge_id             uuid,
    examination_type      uuid,
    examination_status    uuid,
    monitoring_pattern_id uuid
        constraint fk_monitoring_pattern_id
            references monitoring_pattern,
    plan_examination_date timestamp with time zone,
    created_at            timestamp(3),
    updated_at            timestamp(3)
);

create table if not exists task_log
(
    id          uuid
        constraint pk_task_log_id
            primary key,
    contract_id uuid,
    task_id     uuid,
    task_status uuid,
    created_at  timestamp(3),
    updated_at  timestamp(3)
);