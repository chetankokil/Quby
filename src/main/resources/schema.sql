create table if not exists RangeAdvice (
   min decimal(5,2) not null,
   max decimal(5,2) not null,
   advice varchar(40) not null,
  primary key (min,max)
);