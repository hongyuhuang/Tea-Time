/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLTransientConnectionException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.h2.jdbc.JdbcSQLNonTransientConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hongyuhuang
 */
public class DaoExceptionMapper {

    private final Logger logger = LoggerFactory.getLogger(DaoExceptionMapper.class);

    public String messageFromException(Exception exception) {

        // is the error a constraint violation?
        if (exception.getCause() instanceof JdbcSQLIntegrityConstraintViolationException) {

            JdbcSQLIntegrityConstraintViolationException ex
                    = (JdbcSQLIntegrityConstraintViolationException) exception.getCause();

            // is it specifically a unique constraint violation?
            if (ex.getErrorCode() == 23505) {
                return "That username is already in use.";

                // some other form of constraint error - these should have been prevented
                // via OVal, so we should consider this to be a bug
            } else {

                // log the error (since it is a bug that needs to be fixed)
                logger.error("DB constraint error", exception);

                // extract the column name from the message
                String column = exception.getMessage()
                        .replaceAll("(?s).*column \\\"(.*?)\\\";.*", "$1");

                return "The " + column + " field contains invalid data.";
            }

            // is it a connection failure?
        } else if (exception.getCause() instanceof SQLTransientConnectionException
                || exception.getCause() instanceof JdbcSQLNonTransientConnectionException) {

            // possibly a bug, so log it
            logger.error("DB connection error", exception);

            return "The application could not connect to the database.\n\nPlease verify that the database server is running.";

            // some unknown error - most likely a bug, so log it
        } else {
            logger.error("Unrecognised error while accessing DB", exception);

            return "An unrecognised error occurred when accessing the database.";
        }
    }
}
