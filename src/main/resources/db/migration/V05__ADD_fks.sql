ALTER TABLE `eventos` ADD CONSTRAINT `fk_criador` FOREIGN KEY ( `criador_id`) REFERENCES `usuarios` ( `id`);

ALTER TABLE `eventos` ADD CONSTRAINT `fk_local` FOREIGN KEY (`local_id`) REFERENCES `locais` ( `id` );