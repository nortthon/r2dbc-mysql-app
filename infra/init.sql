CREATE TABLE IF NOT EXISTS rrdbc.notes (
    id      BIGINT AUTO_INCREMENT,
    title   VARCHAR(45)  NOT NULL,
    content VARCHAR(500) NOT NULL,
    creation_date datetime NOT NULL,
    identifier varchar(45)  NOT NULL,
    PRIMARY KEY (id),
    INDEX (identifier)
);