-- ============================================================
-- GuardWatch — Datos iniciales completos
-- Base de datos: BDGuardWatch (PostgreSQL)
--
-- INSTRUCCIONES:
--   1. Asegúrate de que la app esté corriendo al menos una vez
--      para que Hibernate cree todas las tablas (ddl-auto=update)
--   2. Abre pgAdmin → BDGuardWatch → Query Tool
--   3. Pega TODO este archivo y ejecuta con F5
--
-- ORDEN DE INSERCIÓN (respeta las FK):
--   1. cliente
--   2. vehiculo
--   3. agente_seguridad
--   4. zona_riesgo
--   5. carga
--   6. servicio_resguardo
--   7. servicio_agente      (tabla puente ManyToMany)
--   8. ruta
--   9. ruta_zona_riesgo     (tabla puente ManyToMany)
--  10. seguimiento
--  11. incidente
--  12. historial_servicio
-- ============================================================


-- ============================================================
-- 1. CLIENTES
-- Empresas y personas que contratan el servicio de resguardo
-- ============================================================
INSERT INTO cliente (razon_social, ruc, direccion, telefono, email_contacto, estado, created_at, updated_at) VALUES
('Logística del Sur SAC',      '20111222333', 'Av. Argentina 3456, Lima',         '014521100', 'contacto@logisticasur.com',   'ACTIVO', NOW(), NOW()),
('Minera Andina del Perú SAC', '20222333444', 'Av. Industrial 789, Ate, Lima',    '014789200', 'operaciones@mineraandina.com','ACTIVO', NOW(), NOW()),
('Transportes Rápidos EIRL',   '20333444555', 'Calle Los Álamos 120, Surquillo',  '014632300', 'gerencia@transrapidos.com',   'ACTIVO', NOW(), NOW()),
('Corporación Vidal SA',       '20444555666', 'Jr. Camaná 999, Cercado de Lima',  '014115400', 'admin@corpvidal.com',         'ACTIVO', NOW(), NOW()),
('Joyería Diamante SRL',       '20555666777', 'Av. La Marina 2002, San Miguel',   '014887500', 'seguridad@joyeriadiamante.com','ACTIVO',NOW(), NOW()),
('Farmacéutica Lima SAC',      '20666777888', 'Av. Próceres 450, San Juan de Lurigancho', '014996600', 'logistica@farmalima.com', 'ACTIVO', NOW(), NOW());


-- ============================================================
-- 2. VEHÍCULOS
-- Flota de la empresa GuardWatch para el transporte
-- ============================================================
INSERT INTO vehiculo (placa, tipo_vehiculo, marca, modelo, anio, capacidad_kg, estado, created_at, updated_at) VALUES
('ABC-123', 'CAMIONETA',   'Toyota',      'Hilux',         2022, 800.0,  'EN_USO',          NOW(), NOW()),
('DEF-456', 'CAMIONETA',   'Mitsubishi',  'L200',          2021, 750.0,  'DISPONIBLE',      NOW(), NOW()),
('GHI-789', 'CAMION',      'Mercedes',    'Actros 2645',   2020, 5000.0, 'DISPONIBLE',      NOW(), NOW()),
('JKL-012', 'MOTOCICLETA', 'Honda',       'CB500X',        2023, 50.0,   'DISPONIBLE',      NOW(), NOW()),
('MNO-345', 'CAMIONETA',   'Ford',        'Ranger',        2022, 900.0,  'EN_USO',          NOW(), NOW()),
('PQR-678', 'SEDAN',       'Toyota',      'Corolla',       2023, 200.0,  'DISPONIBLE',      NOW(), NOW()),
('STU-901', 'CAMION',      'Volvo',       'FH16',          2019, 8000.0, 'EN_MANTENIMIENTO',NOW(), NOW()),
('VWX-234', 'CAMIONETA',   'Nissan',      'Navara',        2021, 820.0,  'DISPONIBLE',      NOW(), NOW());


-- ============================================================
-- 3. AGENTES DE SEGURIDAD
-- Personal de campo disponible para asignar a servicios
-- ============================================================
INSERT INTO agente_seguridad (codigo_agente, dni, nombres, apellidos, telefono, licencia, experiencia_anios, especializacion, disponibilidad, estado, created_at, updated_at) VALUES
('AG-001', '12345678', 'Mario',   'Condori Quispe',  '987001001', 'ARMAS',        8.0, 'CUSTODIA_VALORES',   'OCUPADO',     'ACTIVO', NOW(), NOW()),
('AG-002', '23456789', 'Pedro',   'Salinas Ramos',   '987002002', 'ARMAS',        5.0, 'ESCOLTA',            'OCUPADO',     'ACTIVO', NOW(), NOW()),
('AG-003', '34567890', 'Jorge',   'Mamani Torres',   '987003003', 'MOTOCICLETA',  3.0, 'RUTA',               'DISPONIBLE',  'ACTIVO', NOW(), NOW()),
('AG-004', '45678901', 'Luis',    'Paredes Vega',    '987004004', 'ARMAS',        6.0, 'CUSTODIA_VALORES',   'DISPONIBLE',  'ACTIVO', NOW(), NOW()),
('AG-005', '56789012', 'Carlos',  'Huanca Lima',     '987005005', 'MOTOCICLETA',  4.0, 'RUTA',               'DISPONIBLE',  'ACTIVO', NOW(), NOW()),
('AG-006', '67890123', 'Roberto', 'Flores Castillo', '987006006', 'ARMAS',        9.0, 'CUSTODIA_VALORES',   'OCUPADO',     'ACTIVO', NOW(), NOW()),
('AG-007', '78901234', 'Ernesto', 'Díaz Morales',    '987007007', 'ARMAS',        2.0, 'ESCOLTA',            'DISPONIBLE',  'ACTIVO', NOW(), NOW()),
('AG-008', '89012345', 'Ángel',   'Rodríguez Silva', '987008008', 'MOTOCICLETA',  7.0, 'RUTA',               'DISPONIBLE',  'ACTIVO', NOW(), NOW()),
('AG-009', '90123456', 'Héctor',  'Vargas Mendoza',  '987009009', 'ARMAS',        1.5, 'ESCOLTA',            'DISPONIBLE',  'ACTIVO', NOW(), NOW()),
('AG-010', '01234567', 'Frank',   'Lara Chávez',     '987010010', 'ARMAS',       11.0, 'CUSTODIA_VALORES',   'DE_PERMISO',  'ACTIVO', NOW(), NOW());


-- ============================================================
-- 4. ZONAS DE RIESGO
-- Zonas identificadas con peligro en las rutas del país
-- ============================================================
INSERT INTO zona_riesgo (nombre, descripcion, distrito, provincia, departamento, nivel_riesgo, latitud, longitud, estado, created_at, updated_at) VALUES
('Zona Industrial Ate',         'Alta incidencia de robos a vehículos entre 10pm y 4am',        'Ate',           'Lima',       'Lima',       'ALTO',    -12.026,  -76.919, 'ACTIVO', NOW(), NOW()),
('Carretera Central Km 40-60',  'Tramo con historial de asaltos a camiones de carga pesada',    'Chaclacayo',    'Lima',       'Lima',       'CRITICO', -11.973,  -76.641, 'ACTIVO', NOW(), NOW()),
('Panamericana Sur Km 80-100',  'Zona de baja iluminación y frecuentes robos nocturnos',        'Lurín',         'Lima',       'Lima',       'ALTO',    -12.275,  -76.870, 'ACTIVO', NOW(), NOW()),
('Av. Universitaria - Comas',   'Robos a motocicletas y mensajería en horas de alta afluencia', 'Comas',         'Lima',       'Lima',       'MEDIO',   -11.938,  -77.055, 'ACTIVO', NOW(), NOW()),
('Carretera Huancayo Km 120',   'Zona de neblina permanente y riesgo de asalto en curvas',      'Matucana',      'Huarochirí', 'Lima',       'CRITICO', -11.843,  -76.399, 'ACTIVO', NOW(), NOW()),
('Puerto del Callao - Zona 5',  'Área portuaria con historial de robo de contenedores',         'Callao',        'Callao',     'Callao',     'ALTO',    -12.054,  -77.137, 'ACTIVO', NOW(), NOW()),
('Carretera Ica Km 300-310',    'Zona desierta sin cobertura celular, riesgo de vaciado',       'Ica',           'Ica',        'Ica',        'MEDIO',   -14.067,  -75.730, 'ACTIVO', NOW(), NOW()),
('Av. Trapiche - Carabayllo',   'Zona periférica con robos a camionetas en semáforos',          'Carabayllo',    'Lima',       'Lima',       'MEDIO',   -11.861,  -77.027, 'ACTIVO', NOW(), NOW()),
('Ruta Arequipa Km 900-920',    'Tramo de sierra con riesgo climático y asaltos ocasionales',   'Uchumayo',      'Arequipa',   'Arequipa',   'MEDIO',   -16.413,  -71.660, 'ACTIVO', NOW(), NOW()),
('Carretera Piura Norte',       'Ruta con historial de robos a camiones de alimentos',          'Castilla',      'Piura',      'Piura',      'ALTO',    -5.195,   -80.628, 'ACTIVO', NOW(), NOW());


-- ============================================================
-- 5. CARGAS
-- Cargas de los clientes que serán resguardadas
-- ============================================================
INSERT INTO carga (codigo_carga, descripcion, tipo_carga, valor_estimado, peso_kg, nivel_riesgo, estado, created_at, updated_at, cliente_id) VALUES
('CRG-001', 'Lingotes de oro refinado - Lote 2026-08',            'VALORES',        850000.00, 45.0,   'CRITICO', 'EN_TRANSITO', NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20222333444')),
('CRG-002', 'Medicamentos controlados - Despacho 445',            'FARMACEUTICO',   120000.00, 380.0,  'ALTO',    'EN_TRANSITO', NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20666777888')),
('CRG-003', 'Mercancía general - Carga Lima-Arequipa',            'GENERAL',         18000.00, 2800.0, 'MEDIO',   'EN_TRANSITO', NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20333444555')),
('CRG-004', 'Joyas y relojes de alta gama - Colección Verano',    'VALORES',        320000.00, 12.0,   'CRITICO', 'PENDIENTE',   NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20555666777')),
('CRG-005', 'Repuestos industriales - Pedido corporativo',        'INDUSTRIAL',      55000.00, 1200.0, 'BAJO',    'PENDIENTE',   NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20444555666')),
('CRG-006', 'Efectivo bancario - Reposición cajeros Lima Norte',  'VALORES',        500000.00, 95.0,   'CRITICO', 'PENDIENTE',   NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20111222333')),
('CRG-007', 'Materiales de construcción - Proyecto Miraflores',   'GENERAL',         22000.00, 4500.0, 'BAJO',    'ENTREGADA',   NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20444555666')),
('CRG-008', 'Equipos electrónicos - Importación 2026-07',        'ELECTRONICO',     98000.00, 650.0,  'ALTO',    'ENTREGADA',   NOW(), NOW(), (SELECT id FROM cliente WHERE ruc='20111222333'));


-- ============================================================
-- 6. SERVICIOS DE RESGUARDO
-- El corazón del sistema: une cliente + carga + vehículo
-- ============================================================
INSERT INTO servicio_resguardo (codigo_servicio, tipo_resguardo, fecha_inicio, fecha_fin, estado, prioridad, observaciones, created_at, updated_at, cliente_id, carga_id, vehiculo_id) VALUES
(
  'SRV-2026-001', 'CUSTODIA_VALORES',
  '2026-09-01 06:00:00', NULL,
  'EN_CURSO', 'ALTA',
  'Traslado de lingotes de la mina Andina a bóveda Lima. Ruta: Carretera Central.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20222333444'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-001'),
  (SELECT id FROM vehiculo WHERE placa='ABC-123')
),
(
  'SRV-2026-002', 'CUSTODIA_FARMACEUTICO',
  '2026-09-01 08:30:00', NULL,
  'EN_CURSO', 'ALTA',
  'Medicamentos controlados desde almacén central a sucursales Lima Norte.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20666777888'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-002'),
  (SELECT id FROM vehiculo WHERE placa='MNO-345')
),
(
  'SRV-2026-003', 'ESCOLTA_CARGA',
  '2026-09-02 07:00:00', NULL,
  'PENDIENTE', 'MEDIA',
  'Carga general Lima-Arequipa. Ruta Panamericana Sur.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20333444555'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-003'),
  (SELECT id FROM vehiculo WHERE placa='GHI-789')
),
(
  'SRV-2026-004', 'CUSTODIA_VALORES',
  '2026-09-03 09:00:00', NULL,
  'PENDIENTE', 'ALTA',
  'Joyas y relojes colección verano. Traslado joyería a almacén seguro.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20555666777'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-004'),
  (SELECT id FROM vehiculo WHERE placa='PQR-678')
),
(
  'SRV-2026-005', 'CUSTODIA_VALORES',
  '2026-09-05 05:00:00', NULL,
  'PENDIENTE', 'ALTA',
  'Efectivo bancario para reposición de 12 cajeros Lima Norte. Ruta trazada.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20111222333'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-006'),
  (SELECT id FROM vehiculo WHERE placa='DEF-456')
),
(
  'SRV-2026-006', 'ESCOLTA_INDUSTRIAL',
  '2026-08-25 07:00:00', '2026-08-25 16:00:00',
  'FINALIZADO', 'BAJA',
  'Repuestos industriales entregados sin novedad.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20444555666'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-005'),
  (SELECT id FROM vehiculo WHERE placa='VWX-234')
),
(
  'SRV-2026-007', 'ESCOLTA_CARGA',
  '2026-08-20 08:00:00', '2026-08-20 17:30:00',
  'FINALIZADO', 'BAJA',
  'Materiales de construcción entregados conforme. Sin incidentes.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20444555666'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-007'),
  (SELECT id FROM vehiculo WHERE placa='GHI-789')
),
(
  'SRV-2026-008', 'ESCOLTA_ELECTRONICO',
  '2026-08-15 09:00:00', '2026-08-16 10:00:00',
  'FINALIZADO', 'MEDIA',
  'Equipos electrónicos entregados. Un retraso por tráfico en Av. Javier Prado.',
  NOW(), NOW(),
  (SELECT id FROM cliente WHERE ruc='20111222333'),
  (SELECT id FROM carga  WHERE codigo_carga='CRG-008'),
  (SELECT id FROM vehiculo WHERE placa='MNO-345')
);


-- ============================================================
-- 7. SERVICIO_AGENTE (tabla puente ManyToMany)
-- Asignación de qué agentes cubren qué servicio
-- ============================================================
INSERT INTO servicio_agente (servicio_id, agente_id) VALUES
-- SRV-2026-001: AG-001 (líder custodia) + AG-002 (escolta)
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-001')),
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-002')),
-- SRV-2026-002: AG-006 (custodia) + AG-003 (ruta en moto)
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-006')),
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-003')),
-- SRV-2026-003: AG-004 + AG-005
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-004')),
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-005')),
-- SRV-2026-004: AG-007 solo (servicio de baja carga)
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-004'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-007')),
-- SRV-2026-005: AG-001 (cuando regrese) + AG-008 + AG-009
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-005'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-008')),
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-005'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-009')),
-- SRV-2026-006: AG-004 (ya finalizado)
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-004')),
-- SRV-2026-007: AG-005
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-007'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-005')),
-- SRV-2026-008: AG-006 + AG-007
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-006')),
((SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'), (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-007'));


-- ============================================================
-- 8. RUTAS
-- Una ruta por servicio (relación OneToOne)
-- ============================================================
INSERT INTO ruta (origen, destino, distancia_km, tiempo_estimado_minutos, nivel_riesgo, estado, created_at, updated_at, servicio_id) VALUES
(
  'Lima - Mina Andina (Carretera Central)', 'Bóveda Segura Lima Centro',
  280.0, 360,
  'CRITICO', 'ACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001')
),
(
  'Almacén Central Ate', 'Sucursales Lima Norte (3 puntos)',
  45.0, 90,
  'MEDIO', 'ACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002')
),
(
  'Lima - Terminal Fiori', 'Arequipa - Terminal Terrapuerto',
  1009.0, 780,
  'ALTO', 'ACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003')
),
(
  'Joyería Diamante - San Miguel', 'Almacén Seguro Miraflores',
  12.0, 30,
  'ALTO', 'ACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-004')
),
(
  'Banco Central - San Isidro', '12 cajeros Lima Norte (circuito)',
  55.0, 120,
  'ALTO', 'ACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-005')
),
(
  'Almacén Corpvidal - Lurín', 'Planta industrial Ate',
  38.0, 60,
  'BAJO', 'INACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006')
),
(
  'Depósito San Juan de Lurigancho', 'Obra Miraflores - Av. Larco',
  25.0, 50,
  'BAJO', 'INACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-007')
),
(
  'Puerto del Callao - Zona 5', 'Almacén tecnológico Surco',
  30.0, 70,
  'ALTO', 'INACTIVO', NOW(), NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008')
);


-- ============================================================
-- 9. RUTA_ZONA_RIESGO (tabla puente ManyToMany)
-- Qué zonas de riesgo cruza cada ruta
-- ============================================================
INSERT INTO ruta_zona_riesgo (ruta_id, zona_riesgo_id) VALUES
-- SRV-001 pasa por Carretera Central Km 40-60 y Zona Industrial Ate
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001')), (SELECT id FROM zona_riesgo WHERE nombre='Carretera Central Km 40-60')),
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001')), (SELECT id FROM zona_riesgo WHERE nombre='Zona Industrial Ate')),
-- SRV-002 pasa por Zona Industrial Ate
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002')), (SELECT id FROM zona_riesgo WHERE nombre='Zona Industrial Ate')),
-- SRV-003 pasa por Panamericana Sur y Carretera Ica
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003')), (SELECT id FROM zona_riesgo WHERE nombre='Panamericana Sur Km 80-100')),
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003')), (SELECT id FROM zona_riesgo WHERE nombre='Carretera Ica Km 300-310')),
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003')), (SELECT id FROM zona_riesgo WHERE nombre='Ruta Arequipa Km 900-920')),
-- SRV-005 pasa por Av. Universitaria y Av. Trapiche
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-005')), (SELECT id FROM zona_riesgo WHERE nombre='Av. Universitaria - Comas')),
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-005')), (SELECT id FROM zona_riesgo WHERE nombre='Av. Trapiche - Carabayllo')),
-- SRV-008 pasa por Puerto Callao
((SELECT id FROM ruta WHERE servicio_id=(SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008')), (SELECT id FROM zona_riesgo WHERE nombre='Puerto del Callao - Zona 5'));


-- ============================================================
-- 10. SEGUIMIENTOS GPS
-- Puntos de ubicación registrados por los agentes en ruta
-- ============================================================
INSERT INTO seguimiento (latitud, longitud, velocidad_kmh, estado, fecha_hora, servicio_id, vehiculo_id) VALUES
-- SRV-2026-001: 5 puntos GPS durante el trayecto activo
(-12.026, -76.919, 0.0,   'INICIADO',   '2026-09-01 06:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM vehiculo WHERE placa='ABC-123')),
(-11.990, -76.810, 65.0,  'EN_RUTA',    '2026-09-01 07:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM vehiculo WHERE placa='ABC-123')),
(-11.973, -76.641, 72.5,  'EN_RUTA',    '2026-09-01 08:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM vehiculo WHERE placa='ABC-123')),
(-11.950, -76.500, 55.0,  'EN_RUTA',    '2026-09-01 09:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM vehiculo WHERE placa='ABC-123')),
(-11.920, -76.350, 68.0,  'EN_RUTA',    '2026-09-01 10:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'), (SELECT id FROM vehiculo WHERE placa='ABC-123')),
-- SRV-2026-002: 3 puntos
(-12.026, -76.919, 0.0,   'INICIADO',   '2026-09-01 08:30:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002'), (SELECT id FROM vehiculo WHERE placa='MNO-345')),
(-11.985, -77.025, 48.0,  'EN_RUTA',    '2026-09-01 09:15:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002'), (SELECT id FROM vehiculo WHERE placa='MNO-345')),
(-11.938, -77.055, 40.0,  'EN_RUTA',    '2026-09-01 09:50:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002'), (SELECT id FROM vehiculo WHERE placa='MNO-345')),
-- SRV-2026-006: trayecto finalizado completo
(-12.275, -76.870, 0.0,   'INICIADO',   '2026-08-25 07:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006'), (SELECT id FROM vehiculo WHERE placa='VWX-234')),
(-12.200, -76.820, 70.0,  'EN_RUTA',    '2026-08-25 08:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006'), (SELECT id FROM vehiculo WHERE placa='VWX-234')),
(-12.026, -76.919, 0.0,   'FINALIZADO', '2026-08-25 16:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006'), (SELECT id FROM vehiculo WHERE placa='VWX-234')),
-- SRV-2026-008: trayecto finalizado
(-12.054, -77.137, 0.0,   'INICIADO',   '2026-08-15 09:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'), (SELECT id FROM vehiculo WHERE placa='MNO-345')),
(-12.090, -77.050, 55.0,  'EN_RUTA',    '2026-08-15 10:30:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'), (SELECT id FROM vehiculo WHERE placa='MNO-345')),
(-12.110, -76.995, 30.0,  'EN_RUTA',    '2026-08-15 12:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'), (SELECT id FROM vehiculo WHERE placa='MNO-345')),
(-12.130, -76.960, 0.0,   'FINALIZADO', '2026-08-16 10:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'), (SELECT id FROM vehiculo WHERE placa='MNO-345'));


-- ============================================================
-- 11. INCIDENTES
-- Problemas registrados durante los servicios
-- ============================================================
INSERT INTO incidente (tipo_incidente, descripcion, severidad, latitud, longitud, estado, fecha_hora, created_at, servicio_id, agente_id, zona_riesgo_id) VALUES
(
  'AVERIA_VEHICULO',
  'Pinchazo en llanta delantera derecha en el Km 42 de la Carretera Central. El agente detuvo el vehículo en zona segura. Se coordinó asistencia mecánica.',
  'MEDIA',
  -11.973, -76.641,
  'RESUELTO',
  '2026-09-01 08:45:00', NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'),
  (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-001'),
  (SELECT id FROM zona_riesgo WHERE nombre='Carretera Central Km 40-60')
),
(
  'INTENTO_INTERCEPCION',
  'Vehículo desconocido siguió al camión por aprox. 15 minutos en la Av. Universitaria. El agente tomó ruta alterna. No hubo contacto físico.',
  'ALTA',
  -11.938, -77.055,
  'EN_PROCESO',
  '2026-09-01 09:40:00', NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002'),
  (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-006'),
  (SELECT id FROM zona_riesgo WHERE nombre='Av. Universitaria - Comas')
),
(
  'FALLA_COMUNICACION',
  'El agente AG-002 perdió señal de radio entre el Km 45 y Km 52. Se restableció comunicación via celular a los 12 minutos.',
  'BAJA',
  -11.985, -76.700,
  'RESUELTO',
  '2026-09-01 07:30:00', NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001'),
  (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-002'),
  NULL
),
(
  'RETRASO_RUTA',
  'Tráfico intenso en Av. Javier Prado generó retraso de 90 minutos. La carga llegó en buen estado pero fuera del horario acordado.',
  'BAJA',
  -12.090, -77.050,
  'RESUELTO',
  '2026-08-15 10:30:00', NOW(),
  (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'),
  (SELECT id FROM agente_seguridad WHERE codigo_agente='AG-006'),
  NULL
);


-- ============================================================
-- 12. HISTORIAL DE SERVICIOS
-- Trazabilidad de todos los cambios de estado en cada servicio
-- ============================================================
INSERT INTO historial_servicio (estado_anterior, estado_nuevo, comentario, fecha_hora, servicio_id) VALUES
-- SRV-2026-001: PENDIENTE → EN_CURSO
(NULL,          'PENDIENTE',  'Servicio registrado por el administrador. Agentes asignados: AG-001 y AG-002.',               '2026-08-30 17:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001')),
('PENDIENTE',   'EN_CURSO',   'Servicio iniciado. Vehículo ABC-123 inspeccionado. Carga verificada y sellada.',               '2026-09-01 06:05:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-001')),

-- SRV-2026-002: PENDIENTE → EN_CURSO
(NULL,          'PENDIENTE',  'Servicio creado. Agentes AG-006 y AG-003 asignados.',                                          '2026-08-31 14:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002')),
('PENDIENTE',   'EN_CURSO',   'Carga de medicamentos verificada. Temperatura y sellos correctos. Trayecto iniciado.',         '2026-09-01 08:35:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-002')),

-- SRV-2026-003: solo PENDIENTE por ahora
(NULL,          'PENDIENTE',  'Programado para el 02/09. Agentes AG-004 y AG-005 confirmados.',                               '2026-09-01 10:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-003')),

-- SRV-2026-006: ciclo completo FINALIZADO
(NULL,          'PENDIENTE',  'Servicio creado para traslado de repuestos.',                                                  '2026-08-24 09:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006')),
('PENDIENTE',   'EN_CURSO',   'Carga embarcada. AG-004 reporta condiciones óptimas.',                                         '2026-08-25 07:05:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006')),
('EN_CURSO',    'FINALIZADO', 'Entrega confirmada por receptor. Carga en perfectas condiciones. Sin incidentes.',             '2026-08-25 16:10:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-006')),

-- SRV-2026-007: ciclo completo FINALIZADO
(NULL,          'PENDIENTE',  'Servicio de materiales de construcción programado.',                                           '2026-08-19 08:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-007')),
('PENDIENTE',   'EN_CURSO',   'Materiales embarcados. AG-005 listo.',                                                         '2026-08-20 08:05:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-007')),
('EN_CURSO',    'FINALIZADO', 'Entregado en obra Miraflores. Todo conforme.',                                                 '2026-08-20 17:35:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-007')),

-- SRV-2026-008: ciclo completo con nota de retraso
(NULL,          'PENDIENTE',  'Importación de electrónicos. Servicio programado.',                                            '2026-08-14 11:00:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008')),
('PENDIENTE',   'EN_CURSO',   'Carga retirada del puerto. Contenedor verificado. AG-006 y AG-007 en posición.',               '2026-08-15 09:05:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008')),
('EN_CURSO',    'FINALIZADO', 'Entrega completada con retraso de 90min por tráfico. Cliente notificado. Carga íntegra.',      '2026-08-16 10:10:00', (SELECT id FROM servicio_resguardo WHERE codigo_servicio='SRV-2026-008'));


-- ============================================================
-- VERIFICACIÓN FINAL
-- Ejecuta estas consultas para confirmar que todo se insertó bien
-- ============================================================

SELECT 'cliente'             AS tabla, COUNT(*) AS registros FROM cliente
UNION ALL
SELECT 'vehiculo',                      COUNT(*) FROM vehiculo
UNION ALL
SELECT 'agente_seguridad',              COUNT(*) FROM agente_seguridad
UNION ALL
SELECT 'zona_riesgo',                   COUNT(*) FROM zona_riesgo
UNION ALL
SELECT 'carga',                         COUNT(*) FROM carga
UNION ALL
SELECT 'servicio_resguardo',            COUNT(*) FROM servicio_resguardo
UNION ALL
SELECT 'servicio_agente',               COUNT(*) FROM servicio_agente
UNION ALL
SELECT 'ruta',                          COUNT(*) FROM ruta
UNION ALL
SELECT 'ruta_zona_riesgo',              COUNT(*) FROM ruta_zona_riesgo
UNION ALL
SELECT 'seguimiento',                   COUNT(*) FROM seguimiento
UNION ALL
SELECT 'incidente',                     COUNT(*) FROM incidente
UNION ALL
SELECT 'historial_servicio',            COUNT(*) FROM historial_servicio
ORDER BY tabla;
