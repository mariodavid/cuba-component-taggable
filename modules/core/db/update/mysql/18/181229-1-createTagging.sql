create table DDCT_TAGGING (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    TAG_ID varchar(32) not null,
    TAGGABLE varchar(255) not null,
    TAGGER_ID varchar(32),
    CONTEXT varchar(255),
    --
    primary key (ID)
);
