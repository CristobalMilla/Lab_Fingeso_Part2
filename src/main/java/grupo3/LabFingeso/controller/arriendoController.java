package grupo3.LabFingeso.controller;

import grupo3.LabFingeso.entity.arriendoEntity;
import grupo3.LabFingeso.service.arriendoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/arriendo")
@CrossOrigin
public class arriendoController {
    @Autowired
    private arriendoService serviceArriendo;

    @PostMapping("/paso1/{idVehiculo}")
    public int arriendoPaso1(@PathVariable int idVehiculo, @RequestBody arriendoEntity arriendoFechas){
        return serviceArriendo.arriendoPaso1(idVehiculo, arriendoFechas);
    }

    @PostMapping("/paso2")
    public int arriendoPaso2(){
        return serviceArriendo.arriendoPaso2();
    }

    @PostMapping("/paso3")
    public int arriendoPaso3(@RequestParam int idSucursalFinal, @RequestBody Date fechaCompra){
        return serviceArriendo.arriendoPaso3(idSucursalFinal, fechaCompra);
    }

    @GetMapping("/obtenerPorCliente")
    public List<arriendoEntity> getArriendoByIdUsuario(@RequestParam long idCliente){
        return serviceArriendo.getArriendoByidCliente(idCliente);
    }

    @GetMapping("/obtenerArriendo/{idArriendo}")
    public arriendoEntity getArriendoById(@PathVariable long idArriendo){
        return serviceArriendo.getArriendoById(idArriendo);
    }

    @GetMapping("/obtenerTodos")
    public List<arriendoEntity> getAllArriendos(){
        return serviceArriendo.getAllArriendos();
    }

    @PutMapping("/actualizarArriendo")
    public arriendoEntity updateArriendo(@RequestBody arriendoEntity arriendoModificado){
        return serviceArriendo.updateArriendo(arriendoModificado);
    }

    @PutMapping("/cambiarEstado/{idArriendo}")
    public int updateEstadoArriendo(@PathVariable long idArriendo, @RequestParam String nuevoEstado){
        return serviceArriendo.updateEstadoArriendo(idArriendo, nuevoEstado);
    }

    @DeleteMapping("/eliminarArriendo")
    public boolean eliminateArriendoById(@RequestParam long idArriendo){
        return serviceArriendo.eliminateArriendoById(idArriendo);
    }
}
