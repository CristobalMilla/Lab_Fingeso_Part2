package grupo3.LabFingeso.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permiso")
public class permisoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPermiso;
    private String descripcion;

    public permisoEntity(long idPermiso, String descripcion) {
        this.idPermiso = idPermiso;
        this.descripcion = descripcion;
    }

    public permisoEntity() {

    }

    public long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
