CREATE TABLE tb_schedules (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    date DATE NOT NULL,
    time TIME NOT NULL,
    price DECIMAL(6, 2) NOT NULL,
    status ENUM('CONFIRMADO', 'CONCLUIDO', 'CANCELADO') NOT NULL,
    barber_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    FOREIGN KEY (barber_id) REFERENCES tb_barbers(id),
    FOREIGN KEY (client_id) REFERENCES tb_clients(id),
    FOREIGN KEY (service_id) REFERENCES tb_services(id)
);