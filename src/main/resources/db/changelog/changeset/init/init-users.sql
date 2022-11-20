INSERT INTO users (email, enabled, locked, nickname, password)
VALUES ('superadmin@admin.ru', true, false, 'SuperAdmin',
        '$2a$12$cGlQ09pNfMGZIDUPQra/z.gb0F9WdJsyoqcA77uB7cYZTd.JE6yiK'),
       ('admin@gmail.com', true, false, 'Admin', '$2a$12$umhI5t0j43XnSIzaigGGIuSHX7cQfvSxRcyr3Pnm7U5EM1s.j4Vhu'),
       ('editor@yahoo.com', true, false, 'Editor', '$2a$12$9syMWeR7w1ihWVsINT3mnuZNFxLszAe2rVLwiVZWh8EOEjPrLN3Am'),
       ('user@yandex.ru', true, false, 'User', '$2a$12$HQox3AJmBzCGpkWgsunPcuP5XFNy4wy4OPWpU47xoCpMMokbRK2Uq');

INSERT INTO user_role (user_id, role)
VALUES (1, 'ADMIN'),
       (1, 'EDITOR'),
       (1, 'USER'),
       (2, 'ADMIN'),
       (2, 'USER'),
       (3, 'USER'),
       (3, 'EDITOR'),
       (4, 'USER');