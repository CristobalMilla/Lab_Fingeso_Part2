package grupo3.LabFingeso.repository;

import grupo3.LabFingeso.entity.usuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface usuarioRepository extends JpaRepository<usuarioEntity, Long> {
    usuarioEntity findByCorreo(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE usuario SET existeusuario = false WHERE idusuario = :idUsuario AND existeusuario = true", nativeQuery = true)
    int deleteByIdIfExist(@Param("idUsuario") long idUsuario);
}
