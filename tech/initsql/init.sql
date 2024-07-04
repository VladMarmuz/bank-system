CREATE TABLE loan (
                        id serial,
                        title varchar(25) NOT NULL,
                        description varchar(250) NOT NULL,
                        user_id bigint NOT NULL,
                        PRIMARY KEY (id)
);

CREATE TABLE payment_period (
                                id serial,
                                summa decimal NOT NULL,
                                start_date date NOT NULL,
                                end_date date NOT NULL,
                                status int NOT NULL,
                                loan_id bigint NOT NULL,
                                PRIMARY KEY (id),
                                CONSTRAINT fk_period_credit FOREIGN KEY (loan_id) REFERENCES loan (id)
);