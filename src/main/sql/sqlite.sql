CREATE TABLE "cantina" (
                         "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                         "nome"	TEXT,
                         "regione"	TEXT,
                         "stato"	TEXT,
                         "via"	TEXT,
                         "uvaggio"	TEXT,
                         "idrappresentante"	INTEGER,
                         FOREIGN KEY("idrappresentante") REFERENCES "rappresentante"("ID") on DELETE set NULL on UPDATE CASCADE
);

CREATE TABLE "compito" (
                         "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                         "desc"	TEXT NOT NULL
);

CREATE TABLE "compito_giornaliero" (
                                     "ID"	INTEGER,
                                     "giornoDaRipetere"	TIME,--Ora in cui vada eseguito
                                     PRIMARY KEY("ID"),
                                     FOREIGN KEY("ID") REFERENCES "compito"("ID")
                                       on update cascade
                                       on delete cascade

);
CREATE TABLE "compito_mensile" (
                                 "ID"	INTEGER,
                                 "giornoDaRipetere"	INTEGER CHECK(giornoDaRipetere BETWEEN 0 and 7),
                                 "settimanaDaRipetere"	INTEGER CHECK(settimanaDaRipetere BETWEEN 0 and 4),
                                 PRIMARY KEY("ID"),
                                 FOREIGN KEY("ID") REFERENCES "compito"("ID")
                                   on update cascade
                                   on delete cascade


);

CREATE TABLE "compito_occasionale" (
                                     "ID"	INTEGER,
                                     "dataCompito"	datetime NOT NULL,
                                     PRIMARY KEY("ID"),
                                     FOREIGN KEY("ID") REFERENCES "compito"("ID")
                                       on update cascade
                                       on delete cascade

);

CREATE TABLE "compito_settimanale" (
                                     "ID"	INTEGER,
                                     "giornoDaRipetere"	INTEGER CHECK(giornoDaRipetere BETWEEN 0 and 7),
                                     PRIMARY KEY("ID"),
                                     FOREIGN KEY("ID") REFERENCES "compito"("ID")
                                       on update cascade
                                       on delete cascade

);

CREATE TABLE "compito_svolto" (
                                "idcompito"	INTEGER NOT NULL,
                                "data_compito_svolto"	datetime NOT NULL,
                                "iduser"	INTEGER NOT NULL,
                                "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                                FOREIGN KEY("idcompito") REFERENCES "compito"("ID")
                                  on update cascade
                                  on delete cascade ,
                                FOREIGN KEY("iduser") REFERENCES "users"("ID")
                                  on update cascade
                                  on delete cascade

);
CREATE TABLE "enoteca" (
                         "ID"	INTEGER,
                         "via"	TEXT,
                         "citta"	TEXT,
                         "regione"	TEXT,
                         "stato"	TEXT,
                         PRIMARY KEY("ID"),
                         FOREIGN KEY("ID") REFERENCES "fornitore"("ID") on UPDATE CASCADE on DELETE CASCADE
);

CREATE TABLE "fornitore" (
                           "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                           "nome"	TEXT NOT NULL,
                           "qta_min"	INTEGER DEFAULT 0 CHECK(qta_min>=0),
                           "qta_max"	INTEGER CHECK(qta_max>0),
                           "telefono"	TEXT,
                           "mail"	TEXT
);


CREATE TABLE "operazione" (
                            "idvino"	INTEGER NOT NULL,
                            "data_operazione"	datetime NOT NULL,
                            "qta"	INTEGER,
                            "sconto"	DOUBLE,
                            "importo"	DOUBLE,
                            "descrizione"	text,
                            "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                            FOREIGN KEY("idvino") REFERENCES "vino"("ID") on UPDATE CASCADE on delete cascade
);

CREATE TABLE "rappresentante" (
                                "ID"	INTEGER,
                                "nome_rappresentante"	TEXT,
                                "cognome"	rappresentante TEXT,
                                PRIMARY KEY("ID"),
                                FOREIGN KEY("ID") REFERENCES "fornitore"("ID") on UPDATE CASCADE on DELETE CASCADE
);

CREATE TABLE "tipo_operazione" (
                                 "descrizione"	text UNIQUE NOT NULL ,
                                 "ID"	INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE "tipo_vino" (
                           "tipo"	TEXT UNIQUE NOT NULL ,
                           "ID"	INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE "users" (
                       "ID"	INTEGER PRIMARY KEY AUTOINCREMENT,
                       "nome"	TEXT NOT NULL,
                       "cognome"	TEXT NOT NULL,
                       "email"	TEXT
);

CREATE TABLE "vino" (
                      "ID"	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
                      "nome"	TEXT NOT NULL,
                      "idcantina"	INTEGER NOT NULL,
                      "anno"	INTEGER NOT NULL,
                      "tipo"	TEXT NOT NULL,
                      "qta"	INTEGER NOT NULL DEFAULT 0,
                      "costo"	DOUBLE,
                      "prezzo_vendita"	DOUBLE,
                      FOREIGN KEY("idcantina") REFERENCES "cantina"("ID") on UPDATE CASCADE on delete cascade ,
                      FOREIGN KEY("tipo") REFERENCES "tipo_vino"("tipo") on UPDATE CASCADE on delete cascade
);
