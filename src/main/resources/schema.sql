drop table products if exists cascade;

create table products
(
    id             UUID         not null,
    code           varchar(255) not null,
    name           varchar(255) not null,
    summary        clob         not null,
    description    clob         not null,
    image          varchar(255),
    price          float        not null,
    on_sale        bit(1)       default 0 not null,
    sale_price     float        default 0.0 not null,
    in_stock       bit(1)       default 1 not null,
    time_to_stock  integer      default 0 not null,
    rating         integer      default 1 not null,
    available      bit(1)       default 1 not null,
    primary key (id)
);
