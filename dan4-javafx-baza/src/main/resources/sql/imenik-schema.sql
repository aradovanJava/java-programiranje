-- Shema za aplikaciju Imenik (H2). Izvodi se pri svakom pokretanju, zato IF NOT EXISTS.
CREATE TABLE IF NOT EXISTS kontakt (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    ime      VARCHAR(60)  NOT NULL,
    prezime  VARCHAR(60)  NOT NULL,
    telefon  VARCHAR(30),
    email    VARCHAR(120),
    kreiran  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Početni podaci samo ako je tablica prazna
INSERT INTO kontakt (ime, prezime, telefon, email)
SELECT * FROM (VALUES
    ('Ana',   'Horvat', '091 123 4567', 'ana.horvat@primjer.hr'),
    ('Ivo',   'Kovač',  '098 765 4321', 'ivo.kovac@primjer.hr'),
    ('Maja',  'Babić',  '095 222 3333', 'maja.babic@primjer.hr'),
    ('Luka',  'Marić',  '092 444 5555', 'luka.maric@primjer.hr')
) AS pocetni
WHERE NOT EXISTS (SELECT 1 FROM kontakt);
