package br.com.bucker.model;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Entity;

@Entity
@UserDefinition
public class Usuario extends EntityBase {

    @Username
    public String usuario;
    @Password
    public String senha;
    @Roles
    public String role;

}
