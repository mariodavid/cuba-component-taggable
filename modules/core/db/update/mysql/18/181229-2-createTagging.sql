alter table DDCT_TAGGING add constraint FK_DDCT_TAGGING_ON_TAG foreign key (TAG_ID) references DDCT_TAG(ID);
alter table DDCT_TAGGING add constraint FK_DDCT_TAGGING_ON_TAGGER foreign key (TAGGER_ID) references SEC_USER(ID);
create index IDX_DDCT_TAGGING_ON_TAG on DDCT_TAGGING (TAG_ID);
create index IDX_DDCT_TAGGING_ON_TAGGER on DDCT_TAGGING (TAGGER_ID);
create index IDX_DDCT_TAGGING_ON_TAGGABLE on DDCT_TAGGING (TAGGABLE) ;
