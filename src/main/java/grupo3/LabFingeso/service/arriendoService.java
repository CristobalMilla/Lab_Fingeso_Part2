package grupo3.LabFingeso.service;

import grupo3.LabFingeso.entity.arriendoEntity;
import grupo3.LabFingeso.entity.comprobanteEntity;
import grupo3.LabFingeso.repository.arriendoRepository;
import grupo3.LabFingeso.repository.sucursalRepository;
import grupo3.LabFingeso.repository.vehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class arriendoService {
    @Autowired
    private arriendoRepository arriendoRepo;

    @Autowired
    private vehiculoRepository vehiculoRepo;

    @Autowired
    private sucursalRepository sucursalRepo;

    @Autowired
    private usuarioService usuarioService;

    @Autowired
    private comprobanteService comprService;

    private arriendoEntity arriendoEnProceso;

    public arriendoService(arriendoRepository arriendoRepo, vehiculoRepository vehiculoRepo, usuarioService usuarioService, comprobanteService comprService, sucursalRepository sucursalRepo) {
        this.arriendoRepo = arriendoRepo;
        this.vehiculoRepo = vehiculoRepo;
        this.usuarioService = usuarioService;
        this.comprService = comprService;
        this.sucursalRepo = sucursalRepo;
        arriendoEnProceso = null;
    }

    private boolean esPeriodoValido(Date fechaInicio, Date fechaFin){
        long diferenciaFechas = fechaFin.getTime() - fechaInicio.getTime(); // queda en milisegundos
        long treintaDiasMilisegundos = 2592000000L;
        if(diferenciaFechas > 0 && diferenciaFechas > treintaDiasMilisegundos){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean usuarioTieneArriendoActivo(long idUsuario){
        for(arriendoEntity arriendoUsuario : arriendoRepo.findByidCliente(idUsuario)){
            if(arriendoUsuario != null){
                if(arriendoUsuario.getEstado().equalsIgnoreCase("en uso")
                || arriendoUsuario.getEstado().equalsIgnoreCase("retirar")
                || arriendoUsuario.getEstado().equalsIgnoreCase("retraso")){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean comprobarFechasOtrosArriendos(arriendoEntity nuevoArriendo, long idVehiculo){
        for(arriendoEntity arriendoExistente : arriendoRepo.findByVehiculo(idVehiculo)) {
            if (arriendoExistente.getFechainicio().getTime() > nuevoArriendo.getFechainicio().getTime()
                    || arriendoExistente.getFechainicio().getTime() < nuevoArriendo.getFechafin().getTime()) {
                return true;
            }
        }
        return false;
    }

    public int arriendoPaso1(long idVehiculo, arriendoEntity arriendoFechas){
        try {
            if(usuarioService.getUsuarioActual() == null){
                return 0;// usuario no esta logueado
            }
            if(vehiculoRepo.findByIdIfExist(idVehiculo) == null){
                return 2;// no "existe" el vehiculo en la bd
            }
            if(!(vehiculoRepo.findByIdIfExist(idVehiculo).getEstado().equalsIgnoreCase("disponible"))){
                return 3;// si no esta disponible
            }
            if(usuarioTieneArriendoActivo(usuarioService.getUsuarioActual().getIdUsuario())){
                return 4;// si tiene arriendos activos
            }
            if(comprobarFechasOtrosArriendos(arriendoFechas, idVehiculo)){
                return 5;// si es que el vehiculo no esta reservado entre las fechas
            }
            if(!(esPeriodoValido(arriendoFechas.getFechainicio(), arriendoFechas.getFechafin()))){
                return 6;// si se pasa de los 30 dias
            }
            else {
                arriendoEnProceso = new arriendoEntity();
                arriendoEnProceso.setCliente(usuarioService.getUsuarioById(usuarioService.getIdUsuarioActual()));
                arriendoEnProceso.setVehiculo(vehiculoRepo.findByIdIfExist(idVehiculo));
                arriendoEnProceso.setSucursalrecogida(vehiculoRepo.findByIdIfExist(idVehiculo).getSucursal());
                arriendoEnProceso.setFechafin(arriendoFechas.getFechafin());
                arriendoEnProceso.setFechainicio(arriendoFechas.getFechainicio());
                return 1;
            }
        }
        catch(Exception e){
            arriendoEnProceso = null;
            return 0;
        }
    }

    public int arriendoPaso2(){
        try{
            if(/*Aprobar Pago*/!true || arriendoEnProceso == null
                    || arriendoEnProceso.getCliente().getIdusuario() != usuarioService.getUsuarioActual().getIdUsuario()){
                return 0;
            }
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }
    public int arriendoPaso3(long idSucursalFinal, Date fechaCompra){
        try{
            if(arriendoEnProceso == null){
                return 0;
            }
            else if(arriendoEnProceso.getCliente().getIdusuario() != usuarioService.getUsuarioActual().getIdUsuario()){
                arriendoEnProceso = null;
                return 0;
            }
            if(idSucursalFinal > 0){
                arriendoEnProceso.setSucursaldevolucion(sucursalRepo.findByIdIfExist(idSucursalFinal));
            }
            double costoTotal = arriendoEnProceso.getVehiculo().getPreciobase(); // + algo no me acuerdo
            comprobanteEntity comprobante = new comprobanteEntity();
            comprobante.setFecha(fechaCompra);
            comprobante.setRecibo("RCB-" + arriendoEnProceso.getVehiculo().getIdvehiculo() + usuarioService.getUsuarioActual().getNombreusuario());
            comprobante.setMetodopago("débito");
            comprobante.setMonto(costoTotal);
            arriendoEnProceso.setEstado("retirar");
            arriendoEnProceso.setCostototal(costoTotal);
            comprService.save(comprobante);
            arriendoEnProceso.setComprobante(comprobante);
            arriendoRepo.save(arriendoEnProceso);
            arriendoEnProceso = null;
            return 1;

        }
        catch (Exception e){
            arriendoEnProceso = null;
            return 0;
        }
    }


    public List<arriendoEntity> getArriendoByidCliente(long idUsuario){
        try {
            return arriendoRepo.findByidCliente(idUsuario);
        }
        catch (Exception e) {
            return null;
        }
    }

    public arriendoEntity getArriendoById(long idArriendo){
        return arriendoRepo.findById(idArriendo).orElse(null);
    }

    public List<arriendoEntity> getAllArriendos(){
        return arriendoRepo.findAll();
    }

    public arriendoEntity updateArriendo(arriendoEntity arriendoModificado){
        return arriendoRepo.save(arriendoModificado);
    }

    public int updateEstadoArriendo(long idArriendo, String nuevoEstado){
        try{
            arriendoEntity arriendoExistente = arriendoRepo.findById(idArriendo).orElse(null);
            if(arriendoExistente == null){
                return 0;
            }
            else{
                arriendoExistente.setEstado(nuevoEstado);
                arriendoRepo.save(arriendoExistente);
                return 1;
            }
        }
        catch (Exception e){
            return 0;
        }
    }

    public boolean eliminateArriendoById(long idArriendo){
        if(arriendoRepo.findById(idArriendo).orElse(null) != null){
            try {
                arriendoRepo.deleteById(idArriendo);
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        else{
            return false;
        }
    }
}
