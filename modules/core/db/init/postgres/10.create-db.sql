-- begin DDCT_TAG
create table DDCT_TAG (
    ID uuid,
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
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TAG_ID uuid not null,
    TAGGABLE varchar(255) not null,
    TAGGER_ID uuid,
    CONTEXT varchar(255),
    --
    primary key (ID)
)^
-- end DDCT_TAGGING
