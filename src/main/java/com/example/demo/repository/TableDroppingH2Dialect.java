package com.example.demo.repository;

import org.hibernate.dialect.H2Dialect;

public class TableDroppingH2Dialect extends H2Dialect {
    @Override
    public boolean dropConstraints() {
        return true;
    }
    @Override
    public boolean supportsIfExistsAfterAlterTable() {
        return true;
    }
}
