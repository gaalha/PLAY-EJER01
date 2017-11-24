# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table person (
  idper                         serial not null,
  nombre                        varchar(255),
  edad                          integer,
  delete_at                     timestamp,
  constraint pk_person primary key (idper)
);

create table persona (
  idpersona                     serial not null,
  nombre                        varchar(255),
  edad                          integer,
  constraint pk_persona primary key (idpersona)
);

create table task (
  idtask                        serial not null,
  title                         varchar(255),
  description                   varchar(255),
  id_persona                    integer,
  constraint pk_task primary key (idtask)
);

create table task_two (
  idtasktwo                     serial not null,
  taskname                      varchar(255),
  description                   varchar(255),
  id_person                     integer,
  delete_at                     timestamp,
  constraint pk_task_two primary key (idtasktwo)
);

alter table task add constraint fk_task_id_persona foreign key (id_persona) references persona (idpersona) on delete restrict on update restrict;
create index ix_task_id_persona on task (id_persona);

alter table task_two add constraint fk_task_two_id_person foreign key (id_person) references person (idper) on delete restrict on update restrict;
create index ix_task_two_id_person on task_two (id_person);


# --- !Downs

alter table if exists task drop constraint if exists fk_task_id_persona;
drop index if exists ix_task_id_persona;

alter table if exists task_two drop constraint if exists fk_task_two_id_person;
drop index if exists ix_task_two_id_person;

drop table if exists person cascade;

drop table if exists persona cascade;

drop table if exists task cascade;

drop table if exists task_two cascade;

