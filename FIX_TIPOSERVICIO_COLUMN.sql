-- Fix column name in tiposervicio table to match Hibernate mapping
-- Change from id_tipoServicio (camelCase) to id_tipo_servicio (snake_case)

ALTER TABLE `tiposervicio` 
CHANGE COLUMN `id_tipoServicio` `id_tipo_servicio` INT NOT NULL AUTO_INCREMENT;

-- If the above doesn't work, try this alternative:
-- ALTER TABLE `tiposervicio` 
-- DROP PRIMARY KEY,
-- CHANGE COLUMN `id_tipoServicio` `id_tipo_servicio` INT NOT NULL AUTO_INCREMENT,
-- ADD PRIMARY KEY (`id_tipo_servicio`);
