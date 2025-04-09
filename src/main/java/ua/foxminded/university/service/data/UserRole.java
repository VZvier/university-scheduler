package ua.foxminded.university.service.data;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum UserRole {
    ADMIN("ADMIN"), LECTURER("LECTURER"), STUDENT("STUDENT"), STAFF("STAFF");

    private final String role;
    private static final Map<String, UserRole> eRole;

    UserRole(String role) {
        this.role = role;
    }

    public String getName() {
        return this.role;
    }

    static {
        Map<String, UserRole> map = new ConcurrentHashMap<>();
        for (UserRole instance : UserRole.values()) {
            map.put(instance.getName().toUpperCase(), instance);
        }
        eRole = Collections.unmodifiableMap(map);
    }

    public static UserRole get (String name) {
        return eRole.get(name.toUpperCase());
    }
}
