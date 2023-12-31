INSERT INTO VEHICLE (LICENCE_PLATE, MAKE, MODEL, PROD_YEAR, COLOR, GEAR_TYPE, FUEL_PERCENTAGE)
VALUES ('KE2023AE', 'MAZDA', '6', 2009, 'BLUE', 'MANUAL', 99.5),
       ('AE1590HA', 'TOYOTA', 'COROLLA', 2013, 'YELLOW', 'MANUAL', 89.0),
       ('AE1576BA', 'RENAULT', 'LOGAN', 2020, 'WHITE', 'AUTO', 68.5),
       ('AE8292AA', 'SKODA', 'OCTAVIA', 2014, 'SILVER', 'MANUAL', 68.5),
       ('AE1526KA', 'VOLKSWAGEN', 'POLO', 2018, 'GRAY', 'AUTO', 78.5);

INSERT INTO CUSTOMER (SURNAME, NAME, PATRONYMIC_NAME, BIRTH_DATE, PHONE_NUMBER, PASSWORD, STATUS, RENT_COUNT)
VALUES ('SHEVCHENKO', 'OLEH', 'VASILIYOVICH', '1998-12-15', '+380636991624', 'password', 'ACTIVE', 15),
       ('SOKOLOV', 'IVAN', 'KIRYLOVICH', '1993-10-12', '+380507896158', 'password', 'ACTIVE', 63),
       ('POPOVA', 'OLENA', 'GAVRILIVNA', '2002-01-25', '+380502302009', 'password', 'ACTIVE', 89),
       ('KIDROV', 'MAKUH', 'ABBASOVICH', '1992-05-07', '+380504530978', 'password', 'INACTIVE', 2),
       ('LUCHKOVA', 'SVITLANA', 'OTTELOVNA', '1982-07-26', '+380995067894', 'password', 'ACTIVE', 1),
       ('BEREZA', 'DMYTRO', 'IVANOVICH', '1965-12-15', '+380504530945', 'password', 'ACTIVE', 159);

INSERT INTO PARKING_LOT (NAME, CITY, ADDRESS, ADDITIONAL_PAYMENT, IS_ACTIVE)
VALUES ('DAFI', 'DNIPRO', 'bul. Zorianiy 1a', false, true),
       ('Karavan', 'DNIPRO', 'vul. NighnioDniprovskaya 15', false, true),
       ('Terra Topol', 'DNIPRO', 'vul. Panikakhi 35', false, true),
       ('Shinnaya Parking', 'DNIPRO', 'vul. Shinnaya 15', false, true),
       ('Gagarina Parking', 'DNIPRO', 'vul. Gagarina 169', false, true);

INSERT INTO RENT_HISTORY(VEHICLE_NAME, VEHICLE_ID, START_LOT_NAME, END_LOT_NAME, START_RENT_TIME, END_RENT_TIME, CUSTOMER_ID)
VALUES ('MAZDA 6', 1, 'DAFI', 'KRASNIY PEREC 1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 1);