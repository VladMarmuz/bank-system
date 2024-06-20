package com.banksystem.credit.model;

public enum Status {
    PAYED {
        @Override
        public boolean payed() {
            return true;
        }
    },
    FUTURE_PAYMENT{
        @Override
        public boolean payed() {
            return false;
        }
    },
    OVERDUE {
        @Override
        public boolean payed() {
            return false;
        }
    };

    public abstract boolean payed();
}
