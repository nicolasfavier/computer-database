#!/bin/sh

mysql -uadmincdb -pqwerty1234 computer-database-db-test < src/test/resources/config/PrepDB.sql
