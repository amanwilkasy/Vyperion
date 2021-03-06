package com.vyperion.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class DefaultRoles {
    public static Set<String> USER_ROLE = new HashSet<>(Collections.singletonList("USER"));
    public static Set<String> ADMIN_ROLE = new HashSet<>(Arrays.asList("USER", "ADMIN"));
}
