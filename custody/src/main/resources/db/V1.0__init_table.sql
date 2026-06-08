USE custody;

DROP TABLE IF EXISTS t_prod_base_info;
DROP TABLE IF EXISTS t_product_msg;

CREATE TABLE t_prod_base_info (
    id BIGINT NOT NULL PRIMARY KEY,
    prod_code VARCHAR(32),
    prod_name VARCHAR(100),
    manager_no VARCHAR(32),
    scale DECIMAL(18, 4),
    status VARCHAR(2) DEFAULT '1',
    gmt_create DATETIME,
    gmt_modified DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='托管产品基础表'；

CREATE TABLE t_product_msg (
    id BIGINT NOTNULL PRIMARY KEY,
    msg_no VARCHAR(64) NOT NULL UNIQUE,
    prod_code VARCHAR(32),
    msg_body TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报文记录表'；