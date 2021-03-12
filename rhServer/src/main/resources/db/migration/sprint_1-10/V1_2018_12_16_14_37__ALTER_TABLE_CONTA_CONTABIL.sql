
IF COL_LENGTH('verba', 'id_verba') IS NULL
BEGIN
ALTER TABLE conta_contabil ADD id_verba BIGINT;
ALTER TABLE conta_contabil ADD FOREIGN KEY (id_verba) REFERENCES verba(Id)
END