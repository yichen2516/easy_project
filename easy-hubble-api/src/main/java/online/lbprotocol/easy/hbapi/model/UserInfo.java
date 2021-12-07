package online.lbprotocol.easy.hbapi.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserInfo {
    private Object password;
    private String phone;
    private boolean credentialsNonExpired;
    private int deptId;
    private int tenantId;
    private boolean accountNonExpired;
    private int id;
    private Object avatar;
    private List<AuthoritiesItem> authorities;
    private boolean enabled;
    private String username;
    private boolean accountNonLocked;
}