package com.myspring.core.env;

/**
 * @author Gabriel
 * @since 2023-03-22 22:47
 */
public interface Environment extends PropertyResolver {
    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);
}
