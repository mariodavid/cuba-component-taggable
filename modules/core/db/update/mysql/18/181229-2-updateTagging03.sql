alter table DDCT_TAGGING change column DTYPE DTYPE__U80164 varchar(100)^
alter table DDCT_TAGGING change column CUSTOMER_ID CUSTOMER_ID__U15937 varchar(32)^
drop index IDX_DDCT_TAGGING_ON_CUSTOMER on DDCT_TAGGING ;
