# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table flight_schedule (
  flight_id                     integer not null,
  destination                   varchar(255),
  origin                        varchar(255),
  departure_date                varchar(255),
  departure_time                varchar(255),
  arrival_time                  varchar(255),
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


# --- !Downs

drop table if exists flight_schedule;
drop sequence if exists flight_schedule_seq;

drop table if exists user;

