# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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

alter table task add constraint fk_task_id_persona foreign key (id_persona) references persona (idpersona) on delete restrict on update restrict;
create index ix_task_id_persona on task (id_persona);


# --- !Downs

alter table if exists task drop constraint if exists fk_task_id_persona;
drop index if exists ix_task_id_persona;

drop table if exists persona cascade;

drop table if exists task cascade;

