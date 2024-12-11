package stuscoresystem.sturcture;

import java.util.List;

public class Role {
    private String roleName;
    private List<Permission> permissions;

    public Role(String roleName, List<Permission> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public boolean hasPermission(Permission permission){
        return permissions.contains(permission);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
