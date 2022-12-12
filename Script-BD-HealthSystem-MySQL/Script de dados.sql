-----------------------------------------------------
-- Schema HealthSystem
-- -----------------------------------------------------
CREATE DATABASE `HealthSystem`;
-- -----------------------------------------------------
-- Schema healthsystem
-- -----------------------------------------------------
USE `HealthSystem` ;
-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Empresa`
-- -----------------------------------------------------
CREATE TABLE `HealthSystem`.`Empresa` (
    `idEmpresa` INT NOT NULL AUTO_INCREMENT,
    `razaoSocial` VARCHAR(45) NOT NULL,
    `cnpj` CHAR(14) NOT NULL,
    `logradouro` VARCHAR(45) NOT NULL,
    `numero` INT NOT NULL,
    `bairro` VARCHAR(45) NOT NULL,
    `cidade` VARCHAR(45) NOT NULL,
    `estado` CHAR(2) NOT NULL,
    `cep` CHAR(8) NOT NULL,
    PRIMARY KEY (`idEmpresa`)
);
DESC `HealthSystem`.`Empresa`;
SELECT * FROM `HealthSystem`.`Empresa`;
INSERT INTO `HealthSystem`.`Empresa` VALUES (NULL,"PHILIPS DO BRASIL LTDA",61086336000103,"Avenida Marcos Penteado de Ulhoa Rodrigues",939,"Tambore","Barueri","SP","06460040");

-- -----------------------------------------------------
-- Table `HealthSystem`.`Credencial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HealthSystem`.`Credencial` (
    `idCredencial` INT NOT NULL,
    `tipoCredencial` VARCHAR(45) NOT NULL,
    `nivelPermissao` ENUM('1', '2', '3') NOT NULL,
    PRIMARY KEY (`idCredencial`)
);
DESC `healthsystem`.`Credencial`;
SELECT * FROM `healthsystem`.`Credencial`;
INSERT INTO `healthsystem`.`Credencial` VALUES (323145,"Tecnico",1), (543221,"Analista",2), (386531,"Gerente",3);
-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthsystem`.`Usuario` (`fkEmpresa` INT NOT NULL,
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `fkcredencial` INT NOT NULL,
  PRIMARY KEY(`idUsuario`),
    FOREIGN KEY(`fkEmpresa`)
    REFERENCES `healthsystem`.`Empresa` (`idEmpresa`),
    FOREIGN KEY(`fkcredencial`)
    REFERENCES `healthsystem`.`Credencial` (`idCredencial`)
    );
DESC `HealthSystem`.`Usuario`;
SELECT * FROM `HealthSystem`.`Usuario`;
INSERT INTO `healthsystem`.`Usuario` VALUES (1,NULL,"fernandoBrandao","fernando.brandao@sptech.school","1234",323145);

-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Componente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HealthSystem`.`Componente` (
    `idComponente` INT NOT NULL AUTO_INCREMENT,
    `nomeComponente` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idComponente`)
);
DESC `HealthSystem`.`Componente`;
SELECT * FROM `HealthSystem`.`Componente`;
INSERT INTO `HealthSystem`.`Componente` (`nomeComponente`) VALUES ("CPU"), ("Memoria"), ("Disco");

-- -----------------------------------------------------
-- Table `HealthSystem`.`Filial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthsystem`.`Filial` (
    `idFilial` INT NOT NULL AUTO_INCREMENT,
    `fkEmpresa` INT NOT NULL,
    `nomeFantasia` VARCHAR(45) NOT NULL,
    `logradouro` VARCHAR(45) NOT NULL,
    `numero` INT NOT NULL,
    `bairro` VARCHAR(45) NOT NULL,
    `cidade` VARCHAR(45) NOT NULL,
    `estado` CHAR(2) NOT NULL,
    `cep` CHAR(8) NOT NULL,
    PRIMARY KEY(`idFilial`),
    FOREIGN KEY(`fkEmpresa`)
    REFERENCES `healthsystem`.`Empresa` (`idEmpresa`))
    AUTO_INCREMENT = 1000;
    
DESC `healthsystem`.`filial`;
SELECT * FROM `healthsystem`.`filial`;
INSERT INTO `healthsystem`.`filial` VALUES (NULL,1,"HOSPITAL SAO LUIZ GONZAGA","R MICHEL OUCHANA",94,"JACANA","SÃO PAULO","SP","02276140");
-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Local`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HealthSystem`.`Local` (
    `idLocal` INT NOT NULL AUTO_INCREMENT,
    `identificacao` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idLocal`)
);
DESC `healthsystem`.`Local`;
SELECT * FROM `HealthSystem`.`Local`;
INSERT INTO `healthsystem`.`Local` (`identificacao`) VALUES ("Sala de Ultrassom"),("Enfermaria"),("Sala de Manutenção");
SELECT COUNT(idLocal) FROM `healthsystem`.`local`;
-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Equipamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthsystem`.`Equipamento` (
  `idEquipamento` INT NOT NULL AUTO_INCREMENT,
  `fkFilial` INT NOT NULL,
  `fkLocal` INT NOT NULL,
  `serialNumber` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `modelo` VARCHAR(100) NOT NULL,
  `arqMaquina` VARCHAR(45) NOT NULL,
  `sistemaOp` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEquipamento`),
    FOREIGN KEY (`fkLocal`)
    REFERENCES `healthsystem`.`Local` (`idLocal`),
    FOREIGN KEY (`fkFilial`)
    REFERENCES `healthsystem`.`Filial` (`idFilial`)
    );
DESC `healthsystem`.`Equipamento`;
SELECT * FROM `healthsystem`.`Equipamento`;
SELECT COUNT(idEquipamento) FROM `healthsystem`.`Equipamento`;

-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Parametro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HealthSystem`.`Parametro` (
  `fkEquipamento` INT NOT NULL,
  `fkComponente` INT NOT NULL,
  `idParametro` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(100) NOT NULL,
  `valid` TINYINT NOT NULL,
  PRIMARY KEY (`idParametro`,`fkEquipamento`, `fkComponente`),
  FOREIGN KEY (`fkEquipamento`)
    REFERENCES `HealthSystem`.`Equipamento` (`idEquipamento`),
    FOREIGN KEY (`fkComponente`)
    REFERENCES `HealthSystem`.`Componente` (`idComponente`)
    );
    DESC `healthsystem`.`Parametro`;
    SELECT * FROM `healthsystem`.`Parametro`;

-- -----------------------------------------------------
-- Tabela `HealthSystem`.`Leitura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `HealthSystem`.`Leitura` (
  `fkEquipamento` INT NOT NULL,
  `fkComponente` INT NOT NULL,
  `idLeitura` INT NOT NULL AUTO_INCREMENT,
  `valor` FLOAT NOT NULL,
  `momento` DATETIME NOT NULL,
  PRIMARY KEY (`idLeitura`,`fkEquipamento`, `fkComponente`),
	FOREIGN KEY (`fkEquipamento`)
    REFERENCES `HealthSystem`.`Equipamento` (`idEquipamento`),
    FOREIGN KEY (`fkComponente`)
    REFERENCES `HealthSystem`.`Componente` (`idComponente`)
    
    );
    CREATE TABLE IF NOT EXISTS `HealthSystem`.`ReadDisk` (
  `fkEquipamento` INT NOT NULL,
  `fkComponente` INT NOT NULL,
  `idLeitura` INT NOT NULL AUTO_INCREMENT,
  `Particao` VARCHAR(5) NOT NULL,
  `valor` FLOAT NOT NULL,
  `momento` DATETIME NOT NULL,
  PRIMARY KEY (`idLeitura`,`fkEquipamento`, `fkComponente`),
	FOREIGN KEY (`fkEquipamento`)
    REFERENCES `HealthSystem`.`Equipamento` (`idEquipamento`),
    FOREIGN KEY (`fkComponente`)
    REFERENCES `HealthSystem`.`Componente` (`idComponente`)
    
    );
	DESC `healthsystem`.`Leitura`;

    USE `healthsystem`;
    
    TRUNCATE TABLE `healthsystem`.`Leitura`;
	SELECT * FROM `healthsystem`.`leitura`;
    
    
   INSERT INTO equipamento (fkFilial, fkLocal, serialNumber, nome, modelo, arqMaquina, sistemaOp) VALUES
    (1000, 1,"asd", "nome test", "modelo test", "arq test", "sistema test");
	
    select * from equipamento;
    
    SELECT * FROM Leitura;
    
    
