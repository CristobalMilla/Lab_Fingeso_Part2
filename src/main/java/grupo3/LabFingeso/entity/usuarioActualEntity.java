package grupo3.LabFingeso.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class usuarioActualEntity {
    @Id
    private long idUsuario;
    private String nombreusuario;
    private String perfilactual;

    // Constructor

    public usuarioActualEntity(long idUsuario, String nombreusuario, String perfilactual) {
        this.idUsuario = idUsuario;
        this.nombreusuario = nombreusuario;
        this.perfilactual = perfilactual;
    }

    public usuarioActualEntity(){

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
}
