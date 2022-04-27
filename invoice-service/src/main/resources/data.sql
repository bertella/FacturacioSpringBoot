
INSERT INTO clients(name, lastname, doc_number, date_of_birth, created_at, status) VALUES
('Daniel Eduardo','Gimeno','66666666','1992-12-20',now(),'Active'),
('Ariel','Gonzalez','1111111','1992-12-20',now(),'Active'),
('Federico','Gimenez','22222222','1992-12-20',now(),'Active'),
('Carlos','Fernandez','33333333','1992-12-20',now(),'Active'),
('Agustin','Acosta','44444444','1992-12-20',now(),'Active');

INSERT INTO products(code, description, price_buy, price_sell, stock, status, created_at, last_updated) VALUES
('QWERTY123', 'NoteBook Gamer Legion 5', 18700.45, 250000.0, 10, 'ACTIVE', now(), null),
('ALAKAZAN','NoteBook Gamer ACER NITRO ',12500.45,196000.0, 8, 'ACTIVE', now(), null),
('CTRK0001','NoteBook Gamer HELIOS 300',18700.45,200000.0, 10, 'ACTIVE', now(), null),
('QWerty0001','NoteBook Gamer ASUS TUF 404',18700.45,600000.0, 10, 'ACTIVE', now(), null),
('QWERTY0022','NoteBook Gamer Legion 5 V2',18700.45,70000.0, 10, 'ACTIVE', now(), null),
('QWERTY123345','NoteBook Gamer ASUS TUF REMAKE',18700.45,250000.0, 10, 'ACTIVE', now(), null);


INSERT INTO invoices(created_at, client_id, total) VALUES
(now(), 1 , 450000.0),
(now(), 2 , 796000.0);

INSERT INTO invoice_details(invoice_id, product_id, quantity, unit_price, sub_total) VALUES
(1, 1, 1, 250000, 250000),
(1, 3, 1, 250000, 250000);

INSERT INTO invoice_details(invoice_id, product_id, quantity, unit_price, sub_total) VALUES
(2, 2, 1, 196000, 196000),
(2, 4, 1, 600000, 600000);