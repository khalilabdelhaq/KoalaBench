
###########  data generation #################
-flatlm :

java DBGen json shlm sf10 >yourFolder

-shlm :

java DBGen embeded_json flatlm sf10 >yourFolder

-star :

java DBGen tab star sf10 >yourFolder

###########  data loading #################

-----Oracle Nosql Key value Store -----------

# java -jar <KVHOME>/lib/kvstore.jar kvlite
#java -jar <KVHOME>/lib/sql.jar -helper-hosts <host>:<port> \
#-store <kvstore> load \
#-file /u01/nosql/files/scriptDDL.cli


# You run the scriptDDL script using the load command:

java -jar /u01/nosql/kv-18.1.19/lib/kvstore.jar kvlite

java -jar /u01/nosql/kv-18.1.19/lib/sql.jar -helper-hosts debian:5000 \
-store kvstore load \
-file /u01/nosql/files/scriptDDL.cli

# the script bellow run loadData script which import data from json file

java -jar /u01/nosql/kv-18.1.19/lib/sql.jar -helper-hosts debian:5000 \
-store kvstore load \
-file /u01/nosql/DDL/loadData.cli

------ Oracle relational database ----------

to execute schema definition run : 

SQL> @<filePath>/ssb.sql

to load data run :
 
SQL> @<filePath>ssbTables.sql