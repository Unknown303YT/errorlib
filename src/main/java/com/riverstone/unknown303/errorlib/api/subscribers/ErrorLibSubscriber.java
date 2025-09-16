package com.riverstone.unknown303.errorlib.api.subscribers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A marker class that defines a {@link IErrorLibSubscriber} for later collection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ErrorLibSubscriber {}
