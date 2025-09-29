-- Tablas
CREATE TABLE Pais (Id SERIAL PRIMARY KEY, Nombre VARCHAR(100) NOT NULL);
CREATE TABLE TipoFestivo (Id SERIAL PRIMARY KEY, Tipo VARCHAR(100) NOT NULL);
CREATE TABLE Festivo (Id SERIAL PRIMARY KEY, Nombre VARCHAR(100) NOT NULL, Dia INT NOT NULL, Mes INT NOT NULL, DiasPascua INT NOT NULL, IdTipo INT NOT NULL, IdPais INT NOT NULL, FOREIGN KEY (IdTipo) REFERENCES TipoFestivo(Id), FOREIGN KEY (IdPais) REFERENCES Pais(Id));
CREATE TABLE Tipo (Id SERIAL PRIMARY KEY, Tipo VARCHAR(100) NOT NULL);
CREATE TABLE Calendario (Id SERIAL PRIMARY KEY, Fecha DATE NOT NULL, Descripcion VARCHAR(100) NOT NULL, IdTipo INT NOT NULL, IdPais INT NOT NULL, FOREIGN KEY (IdTipo) REFERENCES Tipo(Id), FOREIGN KEY (IdPais) REFERENCES Pais(Id));

-- Inserción de Datos 
INSERT INTO Pais (Id, Nombre) VALUES (1, 'Colombia');
INSERT INTO TipoFestivo (Id, Tipo) VALUES (1, 'Fijo'), (2, 'Ley de "Puente festivo"'), (3, 'Basado en el domingo de pascua'), (4, 'Basado en el domingo de pascua y Ley de "Puente festivo"');
INSERT INTO Tipo (Id, Tipo) VALUES (1, 'Día laboral'), (2, 'Fin de Semana'), (3, 'Día festivo');
INSERT INTO Festivo (Nombre, Dia, Mes, DiasPascua, IdTipo, IdPais) VALUES
('Año nuevo', 1, 1, 0, 1, 1), ('Santos Reyes', 6, 1, 0, 2, 1), ('San José', 19, 3, 0, 2, 1), ('Jueves Santo', 0, 0, -3, 3, 1),
('Viernes Santo', 0, 0, -2, 3, 1), ('Domingo de Pascua', 0, 0, 0, 3, 1), ('Día del Trabajo', 1, 5, 0, 1, 1), ('Ascensión del Señor', 0, 0, 40, 4, 1),
('Corpus Christi', 0, 0, 61, 4, 1), ('Sagrado Corazón de Jesús', 0, 0, 68, 4, 1), ('San Pedro y San Pablo', 29, 6, 0, 2, 1),
('Independencia Colombia', 20, 7, 0, 1, 1), ('Batalla de Boyacá', 7, 8, 0, 1, 1), ('Asunción de la Virgen', 15, 8, 0, 2, 1),
('Día de la Raza', 12, 10, 0, 2, 1), ('Todos los santos', 1, 11, 0, 2, 1), ('Independencia de Cartagena', 11, 11, 0, 2, 1),
('Inmaculada Concepción', 8, 12, 0, 1, 1), ('Navidad', 25, 12, 0, 1, 1);
