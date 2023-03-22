package com.myspring.core.env;

/**
 * @author Gabriel
 * @since 2023-03-22 22:49
 */
public interface EnvironmentCapable extends Environment {
    Environment getEnvironment();
}
