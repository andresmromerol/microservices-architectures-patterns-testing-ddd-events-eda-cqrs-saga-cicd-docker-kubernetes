package com.amr.shop.cmmj.common_java_context.services.auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public enum RoleEnum {
    USER(UUID.fromString("5c2ced8b-28f9-4476-aa83-4f418088ea8d"), "USER", new PermissionEnum[] {}),
    ADMIN(UUID.fromString("0f3fa24f-0f9b-4ae1-a79a-387be78a3ceb"), "ADMIN", new PermissionEnum[] {
        PermissionEnum.ADMIN_READ,
        PermissionEnum.ADMIN_UPDATE,
        PermissionEnum.ADMIN_DELETE,
        PermissionEnum.ADMIN_CREATE,
        PermissionEnum.MANAGER_READ,
        PermissionEnum.MANAGER_UPDATE,
        PermissionEnum.MANAGER_DELETE,
        PermissionEnum.MANAGER_CREATE
    }),
    MANAGER(UUID.fromString("a161d986-5810-4519-9f8b-33ad3c11f13d"), "MANAGER", new PermissionEnum[] {
        PermissionEnum.MANAGER_READ,
        PermissionEnum.MANAGER_UPDATE,
        PermissionEnum.MANAGER_DELETE,
        PermissionEnum.MANAGER_CREATE
    });

    private final UUID id;
    private final String name;
    private final PermissionEnum[] permissions;

    RoleEnum(UUID id, String name, PermissionEnum[] permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public static Set<RoleEnum> fromUUIDs(Set<UUID> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return Collections.singleton(USER);
        }
        return uuids.stream()
                .map(uuid -> Arrays.stream(RoleEnum.values())
                        .filter(role -> role.getId().equals(uuid))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Role not found with UUID: " + uuid)))
                .collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PermissionEnum[] getPermissions() {
        return permissions;
    }

    public String getValue() {
        return id.toString();
    }
}
