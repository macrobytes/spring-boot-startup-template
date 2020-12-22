package com.app.dao.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.app.dao.AuditModel;
import com.app.dao.tenant.TenantModel;

@Entity
@Table(name = "user")
public class UserModel extends AuditModel {
    private static final long serialVersionUID = 5874657273603466156L;

    public enum Scope {
        ROOT, ORGANIZATION
    }

    public enum Role {
        OWNER, EDITOR, READ
    }

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1)
    private Long id;

    @NotBlank
    @Column(unique = true, updatable = false, nullable = false)
    private String username;

    @NotBlank
    @Column(unique = false, updatable = true, nullable = false)
    private String fullName;

    @NotBlank
    @Column(unique = false, updatable = true, nullable = false)
    private String email;

    @NotBlank
    @Column(unique = false, updatable = true, nullable = false)
    private String hashedPassword;

    @Column(unique = false, updatable = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantModel tenantModel;

    public UserModel() {

    }

    public UserModel(@NotBlank String username, @NotBlank String fullName, @NotBlank String email,
            @NotBlank String hashedPassword, Role role, TenantModel tenantModel) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.tenantModel = tenantModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public TenantModel getTenantModel() {
        return tenantModel;
    }

    public void setTenantModel(TenantModel tenantModel) {
        this.tenantModel = tenantModel;
    }
}
