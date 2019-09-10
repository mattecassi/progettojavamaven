CREATE TABLE "tipo_vino" (
                           "tipo"	TEXT NOT NULL UNIQUE,
                           "ID"	INTEGER PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE "tipo_operazione" (
                                 "descrizione"	text NOT NULL UNIQUE,
                                 "ID"	INTEGER PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE "users" (
                       "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                       "nome"	TEXT NOT NULL,
                       "cognome"	TEXT NOT NULL,
                       "email"	TEXT
);
CREATE TABLE "fornitore" (
                           "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                           "nome"	TEXT NOT NULL UNIQUE ,
                           "qta_min"	INTEGER DEFAULT 0 CHECK(qta_min>=0),
                           "qta_max"	INTEGER CHECK(qta_max>0),
                           "telefono"	TEXT,
                           "mail"	TEXT
);

CREATE TABLE "enoteca" (
                         "ID"	INTEGER NOT NULL,
                         "via"	TEXT,
                         "citta"	TEXT,
                         "regione"	TEXT,
                         "stato"	TEXT,
                         PRIMARY KEY("ID"),
                         FOREIGN KEY("ID") REFERENCES "fornitore"("ID") on UPDATE CASCADE on DELETE CASCADE
);

CREATE TABLE "rappresentante" (
                                "ID"	INTEGER,
                                PRIMARY KEY("ID"),
                                FOREIGN KEY("ID") REFERENCES "fornitore"("ID") on UPDATE CASCADE on DELETE CASCADE
);
CREATE TABLE "cantina" (
                         "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                         "nome"	TEXT not null unique ,
                         "regione"	TEXT,
                         "stato"	TEXT,
                         "via"	TEXT,
                         "uvaggio"	TEXT,
                         "idrappresentante"	INTEGER,
                         FOREIGN KEY("idrappresentante") REFERENCES "rappresentante"("ID") on DELETE set NULL on UPDATE CASCADE
);

CREATE TABLE "compito" (
                         "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                         "descrizione"	TEXT NOT NULL,
                         "dow"	INTEGER CHECK(dow BETWEEN 0 and 6),
                         "dataCompitoOff"	date,
                         CHECK(dataCompitoOff is null or dow is null)
);

CREATE TABLE "compitoSvolto" (
                               "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                               "idCompito"	INTEGER NOT NULL,
                               "dataRisoluzione"	date NOT NULL,
                               "idUser"	INTEGER,
                               FOREIGN KEY("idUser") REFERENCES "users"("ID") on delete cascade on update cascade,
                               FOREIGN KEY("idCompito") REFERENCES "compito"("ID") on delete cascade on update cascade
);

CREATE TABLE "vino" (
                      "ID"	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
                      "nome"	TEXT NOT NULL unique ,
                      "idCantina"	INTEGER ,
                      "anno"	INTEGER NOT NULL,
                      "tipo"	TEXT,
                      "qta"	INTEGER NOT NULL DEFAULT 0,
                      "costo"	DOUBLE,
                      "prezzoVendita"	DOUBLE,
                      "idFornitore"	INTEGER,
                      FOREIGN KEY("idCantina") REFERENCES "cantina"("ID") on UPDATE CASCADE on delete set null,
                      FOREIGN KEY("tipo") REFERENCES "tipo_vino"("tipo") on UPDATE CASCADE on delete set null
);
CREATE TABLE "operazione" (
                            "idvino"	INTEGER NOT NULL,
                            "data_operazione"	datetime NOT NULL,
                            "qta"	INTEGER,
                            "sconto"	DOUBLE,
                            "importo"	DOUBLE,
                            "descrizione"	text,
                            "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                            "tipoOperazione"	INTEGER,
                            FOREIGN KEY("idvino") REFERENCES "vino"("ID") on UPDATE CASCADE on delete CASCADE
);
