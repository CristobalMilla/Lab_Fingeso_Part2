-- Crear comprobante
  DROP TABLE if exists comprobante CASCADE;
CREATE TABLE comprobante (
  id Serial PRIMARY KEY,
  fecha Date NOT NULL,
  monto double precision  NOT NULL,
  metodopago char(8) NOT NULL,
  recibo varchar(30) NOT NULL
);
  -- Crear usuario
  DROP TABLE if exists usuario CASCADE;
  CREATE TABLE usuario (
  idUsuario Serial PRIMARY KEY,
  rut int NOT NULL,
  nombre varchar(30) NOT NULL,
  edad int NOT NULL,
  correo varchar(30) NOT NULL,
  licenciaConducir char(20) NOT NULL,
  carnet char(15) NOT NULL,
  contrasena char(8) NOT NULL,
  existeUsuario BOOLEAN DEFAULT TRUE,
  perfilActual varchar(15)
  );
  -- Crear perfilesDisponibles
  DROP TABLE if exists perfilesDisponibles;
  CREATE TABLE perfilesDisponibles(
  idPerfilesDisponibles Serial PRIMARY KEY,
  perfil char(13) NOT NULL,
  idUsuario Serial NOT NULL references usuario(idUsuario)
  );
  -- Crear sucursal
DROP TABLE if exists sucursal CASCADE;
CREATE TABLE sucursal (
  idSucursal Serial PRIMARY KEY,
  nombre varchar(30) NOT NULL,
  direccion varchar(30) NOT NULL,
  existeSucursal BOOLEAN DEFAULT TRUE
  );
  -- Crear vehiculo
  DROP TABLE if exists vehiculo CASCADE;
CREATE TABLE vehiculo (
  idVehiculo Serial PRIMARY KEY,
  marca char(14) NOT NULL,
  modelo char(14) NOT NULL,
  matricula char(8) NOT NULL,
  kilometraje int NOT NULL,
  estado char(13) NOT NULL,
  preciobase double precision NOT NULL,
  existeVehiculo BOOLEAN DEFAULT TRUE,
  categoria char(1) NOT NULL,
  tipo char(1) NOT NULL,
  transmision char(1) NOT NULL,
  combustible char(1) NOT NULL,
  fotovehiculo varchar,
  idSucursal Serial references sucursal(idSucursal)
  );
 -- Crear arriendo
DROP TABLE if exists arriendo CASCADE;
CREATE TABLE arriendo (
  idArriendo Serial PRIMARY KEY,
  fechaInicio date NOT NULL,
  fechaFin date NOT NULL,
  costoTotal double precision ,
  estado varchar(10) NOT NULL,
  cliente Serial NOT NULL references usuario(idUsuario),
  vehiculo Serial NOT NULL references vehiculo(idVehiculo),
  sucursalRecogida Serial NOT NULL references sucursal(idSucursal),
  sucursalDevolucion Serial NOT NULL references sucursal(idSucursal),
  comprobante Serial references comprobante(id)
  );

-- Poblar tabla sucursal
INSERT INTO sucursal ( nombre, direccion, existeSucursal) VALUES
( 'Sucursal Centro', 'Calle 1, Ciudad X',TRUE),
( 'Sucursal Norte', 'Calle 2, Ciudad Y',TRUE),
( 'Sucursal Sur', 'Calle 3, Ciudad Z',TRUE),
( 'Sucursal Este', 'Calle 4, Ciudad A',TRUE),
( 'Sucursal Oeste', 'Calle 5, Ciudad B',TRUE),
( 'Sucursal Principal', 'Avenida 10, Ciudad X',TRUE),
( 'Sucursal Nueva', 'Calle 6, Ciudad C',TRUE),
( 'Sucursal Rápida', 'Calle 7, Ciudad D',TRUE),
( 'Sucursal Parque', 'Calle 8, Ciudad E',TRUE),
( 'Sucursal Aeropuerto', 'Calle 9, Ciudad F',TRUE);

  -- Poblar tabla vehiculo
INSERT INTO vehiculo (marca, modelo, matricula, kilometraje, estado, preciobase, existeVehiculo, categoria, tipo, transmision, combustible, idSucursal) VALUES
( 'Toyota', 'Aygo', 'ABC1234', 10000, 'Disponible', 20000, TRUE, 'M', 'B', 'M', 'N', 1),
( 'Fiat', '500', 'DEF5678', 15000, 'Reparación', 22000, TRUE, 'N', 'B', 'M', 'N', 2),
( 'Opel', 'Corsa', 'GHI9012', 20000, 'Ocupado', 18000, TRUE, 'E', 'C', 'M', 'R', 3),
( 'Audi', 'A1', 'JKL3456', 12000, 'Disponible', 25000, TRUE, 'H', 'C', 'M', 'R', 4),
( 'Kia', 'Cee''d', 'MNO7890', 17000, 'Reparación', 21000, TRUE, 'C', 'D', 'M', 'R', 5),
( 'Mercedes', 'A Class', 'PQR1234', 22000, 'Ocupado', 30000, TRUE, 'D', 'D', 'M', 'R', 6),
( 'Citroen', 'DS4', 'STU5678', 14000, 'Disponible', 24000, TRUE, 'I', 'D', 'M', 'R', 7),
( 'Mercedes', 'B Class', 'VWX9012', 16000, 'Reparación', 28000, TRUE, 'J', 'D', 'M', 'R', 8),
( 'Ford', 'Mondeo', 'YZA3456', 19000, 'Ocupado', 26000, TRUE, 'S', 'D', 'M', 'R', 9),
( 'Volkswagen', 'Passat', 'BCD7890', 21000, 'Disponible', 32000, TRUE, 'R', 'D', 'M', 'R', 10),
( 'Saab', '9-3', 'EFG1234', 23000, 'Reparación', 27000, TRUE, 'F', 'D', 'M', 'R', 1),
( 'Alfa Romeo', '159', 'HIJ5678', 25000, 'Ocupado', 35000, TRUE, 'G', 'D', 'M', 'R', 2),
( 'BMW', '3 Series', 'KLM9012', 13000, 'Disponible', 33000, TRUE, 'P', 'D', 'M', 'R', 3),
( 'BMW', '4 Series', 'NOP3456', 18000, 'Reparación', 37000, TRUE, 'U', 'D', 'A', 'R', 4),
( 'Mercedes', 'E Class', 'QRS7890', 20000, 'Ocupado', 40000, TRUE, 'L', 'D', 'A', 'R', 5),
( 'BMW', '6 Series', 'TUV1234', 22000, 'Disponible', 45000, TRUE, 'W', 'D', 'M', 'R', 6),
('Lexus', 'LS 4.6 V8', 'WXY5678', 24000, 'Reparación', 50000, TRUE, 'X', 'D', 'A', 'R', 7),
('Kia', 'Rio', 'ZAB9012', 26000, 'Ocupado', 17000, TRUE, 'E', 'C', 'A', 'R', 8),
('Nissan', 'Versa Note', 'CDE3456', 28000, 'Disponible', 19000, TRUE, 'C', 'C', 'A', 'R', 9),
('Toyota', 'Corolla', 'FGH7890', 30000, 'Reparación', 21000, TRUE, 'I', 'C', 'A', 'R', 10),
('Chrysler', '200', 'IJK1234', 32000, 'Ocupado', 23000, TRUE, 'S', 'C', 'A', 'R', 1),
('Nissan', 'Altima', 'LMN5678', 34000, 'Disponible', 25000, TRUE, 'F', 'C', 'A', 'R', 2),
('Chevrolet', 'Impala', 'OPQ9012', 36000, 'Reparación', 27000, TRUE, 'P', 'C', 'A', 'R', 3),
( 'Lincoln', 'MKZ', 'RST3456', 38000, 'Ocupado', 29000, TRUE, 'L', 'C', 'A', 'R', 4),
( 'Infiniti', 'M35', 'UVW7890', 40000, 'Disponible', 31000, TRUE, 'X', 'C', 'A', 'R', 5),
( 'Hummer', 'Hummer', 'XYZ1234', 42000, 'Reparación', 33000, TRUE, 'O', 'C', 'A', 'R', 6);

-- Poblar tabla usuario
INSERT INTO usuario ( rut, nombre, edad, correo,licenciaConducir,carnet, contrasena,existeUsuario, perfilActual) VALUES
(12345678, 'Carlos Pérez', 28, 'carlos@example.com','abcdefg','abcddefg', 'pass1234', TRUE, 'Cliente'),
(98765432, 'Ana Gómez', 34, 'ana@example.com','abcdefg','abcddefg', '1234pass', TRUE,'Empleado'),
(23456789, 'Luis Torres', 45, 'luis@example.com','abcdefg','abcddefg', 'secupass',TRUE, 'Administrador'),
(34567890, 'María Sánchez', 30, 'maria@example.com','abcdefg','abcddefg', 'adm1234', TRUE,'Cliente'),
(45678901, 'Pedro Ruiz', 25, 'pedro@example.com','abcdefg','abcddefg', 'pass4567', TRUE,'Empleado'),
(56789012, 'Elena Martínez', 40, 'elena@example.com','abcdefg','abcddefg', 'pass5678', TRUE,'Administrador'),
(67890123, 'José Díaz', 38, 'jose@example.com','abcdefg','abcddefg', 'pass01',TRUE, 'Cliente'),
(78901234, 'Laura García', 29, 'laura@example.com','abcdefg','abcddefg', 'mypass', TRUE,'Empleado'),
(89012345, 'Javier López', 50, 'javier@example.com','abcdefg','abcddefg', 'qwer123', TRUE,'Administrador'),
(90123456, 'Sofía Fernández', 33, 'sofia@example.com','abcdefg','abcddefg', 'pass7890',TRUE, 'Cliente');
-- Poblar tabla perfilesDisponibles
INSERT INTO perfilesDisponibles (perfil, idUsuario)
VALUES
  ('Cliente', 1),
  ('usuario', 1),
  ('usuario', 2),
  ('usuario', 3),
  ('usuario', 4),
  ('usuario', 5),
  ('usuario', 6),
  ('usuario', 7),
  ('usuario', 8),
  ('usuario', 9),
  ('usuario', 10),
  ('Empleado', 2),
  ('Administrador', 3),
  ('Cliente', 4),
  ('Empleado', 5),
  ('Administrador', 6),
  ('Cliente', 7),
  ('Empleado', 8),
  ('Administrador', 9),
  ('Cliente', 10);
-- Poblar tabla comprobante
INSERT INTO comprobante (fecha, monto, metodopago, recibo) VALUES
('2024-12-31', 11534, 'efectivo', 'RCB-7452'),  -- Toyota Aygo
('2024-01-31', 17231, 'débito', 'RCB-1893'),   -- Fiat 500
('2025-02-28', 24123, 'crédito', 'RCB-5271'),  -- Opel Corsa
('2024-03-31', 15231, 'efectivo', 'RCB-6342'), -- Audi A1
('2025-04-29', 18502, 'débito', 'RCB-9931'),   -- Kia Cee'd
('2024-05-31', 23754, 'crédito', 'RCB-2745'),  -- Mercedes A Class
('2025-06-30', 16635, 'efectivo', 'RCB-8764'), -- Citroen DS4
('2024-07-31', 19987, 'débito', 'RCB-4932'),   -- Mercedes B Class
('2025-08-30', 24101, 'crédito', 'RCB-1685'),  -- Ford Mondeo
('2024-09-30', 23837, 'efectivo', 'RCB-3107'); -- Volkswagen Passat
-- Poblar tabla arriendo
INSERT INTO arriendo ( fechaInicio, fechaFin, costoTotal, estado, cliente, vehiculo, sucursalRecogida, sucursalDevolucion, comprobante) VALUES
  ( '2025-01-01', '2025-01-10', 100.50, 'en uso', 1, 1, 1, 2, 1),  -- Toyota Aygo
  ( '2024-02-01', '2024-02-15', 150.75, 'terminado', 2, 2, 2, 3, 2), -- Fiat 500
  ( '2025-03-01', '2025-03-10', 200.00, 'retirar', 3, 3, 3, 4, 3), -- Opel Corsa
  ( '2024-04-01', '2024-04-05', 80.30, 'terminado', 4, 4, 4, 5, 1), -- Audi A1
  ( '2025-05-01', '2025-05-07', 120.60, 'retirar', 5, 5, 5, 6, 2), -- Kia Cee'd
  ( '2024-06-01', '2024-06-10', 180.90, 'terminado', 6, 6, 6, 7, 3), -- Mercedes A Class
  ( '2025-07-01', '2025-07-15', 250.00, 'retirar', 7, 7, 7, 8, 1), -- Citroen DS4
  ( '2024-08-01', '2024-08-10', 300.00, 'terminado', 8, 8, 8, 9, 2), -- Mercedes B Class
  ( '2025-09-01', '2025-09-07', 110.50, 'retirar', 9, 9, 9, 10, 3), -- Ford Mondeo
  ( '2024-10-01', '2024-10-15', 90.75, 'terminado', 10, 10, 10, 1, 1); -- Volkswagen Passat