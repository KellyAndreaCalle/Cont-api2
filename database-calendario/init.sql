CREATE TABLE Tipo (
    Id SERIAL PRIMARY KEY,
    Tipo VARCHAR(100) NOT NULL
);

CREATE TABLE Calendario (
    Id SERIAL PRIMARY KEY,
    Fecha DATE NOT NULL,
    Descripcion VARCHAR(100) NOT NULL,
    IdTipo INT NOT NULL,
    FOREIGN KEY (IdTipo) REFERENCES Tipo(Id)
);

-- Insertamos los 3 tipos de día que usaremos
INSERT INTO Tipo (Id, Tipo) VALUES (1, 'Día laboral'), (2, 'Fin de Semana'), (3, 'Día festivo');