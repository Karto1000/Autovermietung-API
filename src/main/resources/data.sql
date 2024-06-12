INSERT INTO permission (id, name)
VALUES (1, 'create:car'),
       (2, 'delete:car'),
       (3, 'edit:car'),
       (4, 'view:car'),
       (5, 'rent:car'),
       (6, 'delete:firm'),
       (7, 'view:firm'),
       (8, 'create:place'),
       (9, 'delete:place'),
       (10, 'edit:place'),
       (11, 'view:place'),
       (13, 'delete:rental'),
       (15, 'view:rental'),
       (16, 'view:carNotRented');

INSERT INTO role (id, name)
VALUES (1, 'admin'),
       (2, 'user'),
       (3, 'firm');

# Admin
INSERT INTO role_permission (role_id, permission_id)
VALUES (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11);

# User
INSERT INTO role_permission (role_id, permission_id)
VALUES (2, 4),
       (2, 5),
       (2, 7),
       (2, 11),
       (2, 15),
       (2, 16),
       (2, 13);

# Firm
INSERT INTO role_permission (role_id, permission_id)
VALUES (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 7),
       (3, 11),
       (3, 15);

INSERT INTO user (id, email, role_id)
VALUES (1, 'admin@admin.com', 1);

INSERT INTO user (id, email, role_id)
VALUES (2, 'user@user.com', 2);

INSERT INTO user (id, email, role_id)
VALUES (3, 'firm@firm.com', 3);

INSERT INTO place (id, name, plz)
VALUES (1, 'Place', 4500);

INSERT INTO firm (id, name, user_id, place_id)
VALUES (1, 'Firm', 3, 1);

INSERT INTO car (id, brand, model, firm_id, price_per_hour)
VALUES (2, 'Audi', 1, 1, 10);
