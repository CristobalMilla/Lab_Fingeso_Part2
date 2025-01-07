package grupo3.LabFingeso.repository;

import grupo3.LabFingeso.entity.vehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface vehiculoRepository extends JpaRepository<vehiculoEntity, Long> {
    @Query(value = "SELECT * FROM vehiculo WHERE idvehiculo = :idvehiculo AND existevehiculo = true", nativeQuery = true)
    vehiculoEntity findByIdIfExist(@Param("idvehiculo") long idVehiculo);

    @Query(value = "SELECT * FROM vehiculo WHERE existevehiculo = true GROUP BY idvehiculo ORDER BY idvehiculo ASC", nativeQuery = true)
    List<vehiculoEntity> findAllThatExists();

    List<vehiculoEntity> findAllByEstado(String estado);

    @Query(value = "SELECT * FROM vehiculo WHERE idsucursal=:idsucursal AND existevehiculo = true", nativeQuery = true)
    List<vehiculoEntity> findAllBySucursalId(@Param("idsucursal") long idSucursal);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehiculo SET existevehiculo = false WHERE idvehiculo = :idvehiculo AND existevehiculo = true", nativeQuery = true)
    int deleteById(@Param("idvehiculo") long idVehiculo);
}
