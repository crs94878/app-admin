package ru.sshell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class AbstractDao {

    @NonNull
    protected final NamedParameterJdbcTemplate parameterJdbcTemplate;

    public AbstractDao(NamedParameterJdbcTemplate parameterJdbcTemplate) {
        this.parameterJdbcTemplate = parameterJdbcTemplate;
    }
}
