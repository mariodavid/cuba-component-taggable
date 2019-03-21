-- begin DDCT_TAG
create table DDCT_TAG (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    VALUE_ varchar(255) not null,
    CONTEXT varchar(255),
    --
    primary key (ID)
)^
-- end DDCT_TAG
-- begin DDCT_TAGGING
create table DDCT_TAGGING (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TAG_ID varchar(36) not null,
    TAGGABLE varchar(255) not null,
    TAGGER_ID varchar(36),
    CONTEXT varchar(255),
    --
    primary key (ID)
)^
-- end DDCT_TAGGING
-- begin DDCT_CUSTOMER
create table DDCT_CUSTOMER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end DDCT_CUSTOMER
