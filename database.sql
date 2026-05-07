USE hospital_dragon;

-- Tabla Presentación
CREATE TABLE tbl_presentacion (
    id_presentacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre_presentacion VARCHAR(50) NOT NULL
);

-- Tabla Inventario
CREATE TABLE tbl_inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_medicamento VARCHAR(100) NOT NULL,
    id_presentacion INT NOT NULL,
    stock INT NOT NULL,
    nivel_minimo INT NOT NULL,
    tipo_distribucion ENUM('Libre', 'Receta') NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    FOREIGN KEY (id_presentacion) REFERENCES tbl_presentacion(id_presentacion)
);

-- Registros de prueba iniciales
INSERT INTO tbl_presentacion (nombre_presentacion) VALUES ('Ampolleta'), ('Tableta'), ('Solución'), ('Cápsula'), ('Jarabe'), ('Suspension'), ('Inahalador');

INSERT INTO tbl_inventario (nombre_medicamento, id_presentacion, stock, nivel_minimo, tipo_distribucion, fecha_vencimiento) 
VALUES 
('Paracetamol', 2, 50, 10, 'Libre', '2027-10-15'),
('Clonazepam', 2, 5, 15, 'Receta', '2026-12-01'),
('Ketorolaco', 1, 20, 20, 'Libre', '2028-01-20'),
('Loratadina', 2, 100, 20, 'Libre', '2028-09-13'),
('Salbutamol', 7, 50, 10, 'Libre', '2027-05-30');