package grupo3.LabFingeso.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class usuarioActualEntity {
    @Id
    private long idUsuario;
    private String nombreusuario;
    private String perfilactual;
    private String correoUsuario;

    // Constructor

    public usuarioActualEntity(long idUsuario, String nombreusuario, String perfilactual, String correoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreusuario = nombreusuario;
        this.perfilactual = perfilactual;
        this.correoUsuario = correoUsuario;
    }

    public usuarioActualEntity() {

    }

    // GETTERS

    public long getIdUsuario() {
        return idUsuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public String getPerfilactual() {
        return perfilactual;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    // SETTERS

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public void setPerfilactual(String perfilactual) {
        this.perfilactual = perfilactual;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
}
