package grupo3.LabFingeso.repository;

import grupo3.LabFingeso.entity.sucursalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface sucursalRepository extends JpaRepository<sucursalEntity, Long> {
    @Query(value = "SELECT * FROM sucursal WHERE idsucursal = :idsucursal AND existesucursal = true", nativeQuery = true)
    sucursalEntity findByIdIfExist(@Param("idsucursal") long idSucursal);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sucursal SET existesucursal = false WHERE idsucursal = :idSucursal AND existesucursal = true", nativeQuery = true)
    int deleteByIdIfExist(@Param("idSucursal")long idSucursal);
}
