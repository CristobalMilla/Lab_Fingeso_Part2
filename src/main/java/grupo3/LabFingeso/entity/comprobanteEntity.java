package grupo3.LabFingeso.entity;

import java.util.Date;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comprobante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class comprobanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Date fecha;
    private double monto;
    private String metodopago;
    private String recibo;
    
    //ver dependencias circulares (?)
    /*
    @OneToOne
    @JoinColumn(name = "arriendo_id")
    @JsonBackReference
    private arriendoEntity arriendo;
    */

}
