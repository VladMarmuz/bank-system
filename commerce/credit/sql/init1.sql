CREATE TABLE credit (
                        id serial PRIMARY KEY,
                        title varchar(25) NOT NULL,
                        description varchar(250) NOT NULL,
                        user_id bigint NOT NULL
);

CREATE TABLE payment_period (
                                id serial PRIMARY KEY,
                                summa decimal NOT NULL,
                                start_date date NOT NULL,
                                end_date date NOT NULL,
                                status int NOT NULL,
                                credit_id bigint NOT NULL,
                                CONSTRAINT fk_period_credit FOREIGN KEY (credit_id) REFERENCES credit (id)
);