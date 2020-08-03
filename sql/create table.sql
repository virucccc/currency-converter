CREATE TABLE IF NOT EXISTS users (
	id BIGSERIAL PRIMARY KEY,
	username VARCHAR(32),
	password VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS valutes (
	id INTEGER PRIMARY KEY,
	fullname VARCHAR(64),
	shortname VARCHAR(3)
);

CREATE TABLE IF NOT EXISTS exchanges (
	id BIGSERIAL PRIMARY KEY,
	value REAL NOT NULL,
	valute_id INTEGER NULL,
	date DATE NOT NULL,
	FOREIGN KEY(valute_id) REFERENCES valutes(id)
);

CREATE TABLE IF NOT EXISTS histories (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	exchange_from BIGINT NOT NULL,
	exchange_to BIGINT NOT NULL,
	amount_from REAL NOT NULL,
	amount_to REAL NOT NULL,
	date DATE NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id),
	FOREIGN KEY(exchange_from) REFERENCES exchanges(id),
	FOREIGN KEY(exchange_to) REFERENCES exchanges(id),
	UNIQUE(user_id, exchange_from, exchange_to, amount_from, amount_to, date)
);