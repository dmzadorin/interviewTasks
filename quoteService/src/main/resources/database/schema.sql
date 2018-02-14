create table energy_level
(
  id bigint auto_increment,
  ISIN varchar(255) not null,
  VALUE varchar(255) not null,
);

create table quote
(
  id bigint auto_increment,
  ISIN varchar(255) not null,
  BID DOUBLE,
  ASK DOUBLE,
  BIDSIZE integer,
  ASKSIZE integer,
);