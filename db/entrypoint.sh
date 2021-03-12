#!/bin/bash

DB="rhlinkcon"
TIME=15s

echo Starting in $TIME...
sleep $TIME
/opt/mssql-tools/bin/sqlcmd -S 0.0.0.0 -U SA -P ${SA_PASSWORD} -Q "RESTORE DATABASE [${DB}] FROM DISK = '/var/opt/mssql/backup/dump.bak' WITH REPLACE, MOVE '${DB}' TO '/var/opt/mssql/data/${DB}.mdf', MOVE '${DB}_log' TO '/var/opt/mssql/data/${DB}_log.ldf'"

echo Trying populate in $TIME...
sleep $TIME
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P ${SA_PASSWORD} -d ${DB} -i populate.sql
