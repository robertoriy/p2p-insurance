CREATE TABLE IF NOT EXISTS user_profile (
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(30)  CHECK (LENGTH(username) >= 6 AND LENGTH(username) <= 30) UNIQUE NOT NULL,
    password VARCHAR(16) NOT NULL,
    first_name VARCHAR(20),
    surname VARCHAR(20),
    date_of_birth DATE,
    ethereum_address VARCHAR(42)
);

CREATE TABLE IF NOT EXISTS insurance_contract (
    id SERIAL PRIMARY KEY NOT NULL,
    address VARCHAR(42) NOT NULL,
    contribute_value INT NOT NULL
);

CREATE TABLE IF NOT EXISTS insurance_group (
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(32) CHECK (LENGTH(title) >= 3) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    contract_id INT REFERENCES insurance_contract(id) UNIQUE,
    created_by INT REFERENCES user_profile(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_group_relation (
    user_id INT REFERENCES user_profile(id) NOT NULL,
    group_id INT REFERENCES insurance_group(id) NOT NULL,
    PRIMARY KEY(user_id, group_id)
);

CREATE TABLE IF NOT EXISTS insurance_transaction (
    id SERIAL PRIMARY KEY NOT NULL,
    contract_id INT REFERENCES insurance_contract(id) NOT NULL,
    event_type VARCHAR(20) CHECK (event_type in ('contribute', 'refund', 'report')) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);
