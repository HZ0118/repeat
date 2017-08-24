# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table destination (
  id                            bigint not null,
  destination                   varchar(255),
  constraint pk_destination primary key (id)
);
create sequence destination_seq;

create table flight_schedule (
  flight_id                     bigint not null,
  origin                        varchar(255),
  destination_id                bigint,
  city                          varchar(255),
  departure_date                timestamp,
  departure_time                varchar(255),
  arrival_time                  varchar(255),
  seats                         integer,
  constraint pk_flight_schedule primary key (flight_id)
);
create sequence flight_schedule_seq;

create table user (
  role                          varchar(255),
  email                         varchar(255) not null,
  name                          varchar(255),
  password                      varchar(255),
  constraint pk_user primary key (email)
);

alter table flight_schedule add constraint fk_flight_schedule_destination_id foreign key (destination_id) references destination (id) on delete restrict on update restrict;
create index ix_flight_schedule_destination_id on flight_schedule (destination_id);


# --- !Downs

alter table flight_schedule drop constraint if exists fk_flight_schedule_destination_id;
drop index if exists ix_flight_schedule_destination_id;

drop table if exists destination;
drop sequence if exists destination_seq;

drop table if exists flight_schedule;
drop sequence if exists flight_schedule_seq;

drop table if exists user;

