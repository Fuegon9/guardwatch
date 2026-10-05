-- ============================================================
-- data-inicial.sql — GuardWatch  (OPCIONAL)
--
-- Desde esta versión la aplicación crea sola los roles y usuarios
-- al arrancar (clase security/DataInitializer.java), así que NO
-- necesitas ejecutar este script para poder hacer login.
--
-- Se conserva por si el laboratorio pide entregarlo. Es seguro
-- ejecutarlo aunque la app ya haya creado los datos: no duplica
-- nada (ON CONFLICT / NOT EXISTS).
--
-- Contraseña de los 4 usuarios: 12345  (hash BCrypt, 10 rounds)
-- ============================================================

INSERT INTO roles(name) VALUES
    ('ADMINISTRADOR'),
    ('AGENTE'),
    ('CLIENTE_VIP'),
    ('CLIENTE_NORMAL')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users(username, password, enabled) VALUES
    ('admin_gw',       '$2a$10$pQeZla3780Hhi38rLnuRvuJuppfpE0EVeFQ5xFLEvEWKzNzpgzv4.', true),
    ('agente_gw',      '$2a$10$pQeZla3780Hhi38rLnuRvuJuppfpE0EVeFQ5xFLEvEWKzNzpgzv4.', true),
    ('cliente_vip_gw', '$2a$10$pQeZla3780Hhi38rLnuRvuJuppfpE0EVeFQ5xFLEvEWKzNzpgzv4.', true),
    ('cliente_gw',     '$2a$10$pQeZla3780Hhi38rLnuRvuJuppfpE0EVeFQ5xFLEvEWKzNzpgzv4.', true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO user_role(user_id, role_id)
SELECT u.id, r.id
FROM (VALUES
        ('admin_gw',       'ADMINISTRADOR'),
        ('agente_gw',      'AGENTE'),
        ('cliente_vip_gw', 'CLIENTE_VIP'),
        ('cliente_gw',     'CLIENTE_NORMAL')
     ) AS m(username, rol)
JOIN users u ON u.username = m.username
JOIN roles r ON r.name     = m.rol
WHERE NOT EXISTS (
    SELECT 1 FROM user_role ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);

-- Verificación: debe mostrar 4 filas
SELECT u.username, r.name AS rol
FROM users u
JOIN user_role ur ON ur.user_id = u.id
JOIN roles r      ON r.id       = ur.role_id
ORDER BY u.username;
