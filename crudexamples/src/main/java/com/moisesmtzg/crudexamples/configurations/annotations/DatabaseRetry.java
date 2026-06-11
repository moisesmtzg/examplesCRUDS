package com.moisesmtzg.crudexamples.configurations.annotations;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.CannotCreateTransactionException;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Retryable(
        retryFor = {CannotCreateTransactionException.class},
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
        //maxAttempts  by default is 3
)
public @interface DatabaseRetry {
}
