package grupo3.LabFingeso.service;

import grupo3.LabFingeso.entity.usuarioActualEntity;
import grupo3.LabFingeso.entity.usuarioEntity;
import grupo3.LabFingeso.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class usuarioService {
    @Autowired
    private final usuarioRepository usuarioRepo;


    private usuarioActualEntity usuarioActualSesion;

    public usuarioService(usuarioRepository usuarioRepo) {
        this.usuarioActualSesion = new usuarioActualEntity();
        this.usuarioRepo = usuarioRepo;
    }

    public usuarioEntity registro(int rut, String nombre, int edad,
                                  String correo, String carnet, String licenciaConducir,
                                  String contrasena) {
        usuarioEntity usuario = new usuarioEntity(rut, nombre, edad, correo, carnet, licenciaConducir, contrasena);
        usuarioEntity existente = usuarioRepo.findByCorreo(usuario.getCorreo());
        if (existente != null){
            return null;
        } else {
            return usuarioRepo.save(usuario);
        }
    }

    public int login(String correo, String contrasena) {
        usuarioEntity usuarioActual;
        try {
            usuarioActual = usuarioRepo.findByCorreo(correo);
            usuarioActual.setPerfilactual(null);
        } catch (Exception e) {
            System.out.println("Usuario no encontrado con el correo");
            return 0;
        }
        if (usuarioActual.getContrasena().equals(contrasena)) {
            if (usuarioActual.getPerfilactual() == null || usuarioActual.getPerfilactual().isEmpty()) {
                try {
                    usuarioActualSesion.setIdUsuario(usuarioActual.getIdusuario());
                    usuarioActualSesion.setNombreusuario(usuarioActual.getNombre());
                    usuarioActualSesion.setCorreoUsuario(usuarioActual.getCorreo());
                    return 1;
                }
                catch (Exception e){
                    System.out.println("Error al asignar al usuario como usuario actual en la sesion");
                    return 0;
                }

            } else {
                return 0;
            }
        }else{
            System.out.println("Contraseña incorrecta");
            return 0;
        }
    }

    public int logout(){
        try{
            usuarioEntity usuarioActual = getUsuarioById(this.usuarioActualSesion.getIdUsuario());
            usuarioActual.setPerfilactual(null);
            this.usuarioActualSesion = new usuarioActualEntity();
            this.usuarioRepo.save(usuarioActual);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    public void elegirPerfil(String correo, String cambioPerfil) {
        usuarioEntity usuario = usuarioRepo.findByCorreo(correo);
        List<String> perfilesDisponibles = usuario.getPerfilesdisponibles();

        try{
            if(cambioPerfil.equalsIgnoreCase("Desarrollador") || cambioPerfil.equalsIgnoreCase("Administrador")
                    || cambioPerfil.equalsIgnoreCase("Empleado") || cambioPerfil.equalsIgnoreCase("Cliente")
                    || cambioPerfil.equalsIgnoreCase("Usuario")){

                try {
                    if (perfilesDisponibles.contains(cambioPerfil)) {
                        usuario.setPerfilactual(cambioPerfil);
                        usuarioRepo.save(usuario);
                        usuarioActualSesion.setPerfilactual(cambioPerfil);
                    }
                } catch (Exception e) {
                    System.out.println("Perfil elegirPerfil no encontrado dentro de los perfiles disponibles del usuario");
                }
            }
        } catch (Exception e) {
            System.out.println("Perfil elegirPerfil no encontrado en el sistema");
        }
    }

    public void cambiarPerfil(String correo, String cambioPerfil) {
        usuarioEntity usuario = usuarioRepo.findByCorreo(correo);
        List<String> perfilesDisponibles = usuario.getPerfilesdisponibles();

        try{
            if(cambioPerfil.equalsIgnoreCase("Desarrollador") || cambioPerfil.equalsIgnoreCase("Administrador")
                    || cambioPerfil.equalsIgnoreCase("Empleado") || cambioPerfil.equalsIgnoreCase("Cliente")
                    || cambioPerfil.equalsIgnoreCase("Usuario")){

                try {
                    if (perfilesDisponibles.contains(cambioPerfil)) {
                        usuario.setPerfilactual(cambioPerfil);
                        usuarioRepo.save(usuario);
                        usuarioActualSesion.setPerfilactual(cambioPerfil);
                    }
                } catch (Exception e) {
                    System.out.println("Perfil elegirPerfil no encontrado dentro de los perfiles disponibles del usuario");
                }
            }
        } catch (Exception e) {
            System.out.println("Perfil elegirPerfil no encontrado en el sistema");
        }
    }

    public void agregarPerfilAUsuarioExistente(String correo, String agregarPerfil, String correoHabilitador) {
        usuarioEntity usuario = usuarioRepo.findByCorreo(correo);
        usuarioEntity usuarioActual = usuarioRepo.findByCorreo(correoHabilitador);

        try{
            if(agregarPerfil.equalsIgnoreCase("Cliente") || agregarPerfil.equalsIgnoreCase("Administrador")
                    || agregarPerfil.equalsIgnoreCase("Empleado")|| agregarPerfil.equalsIgnoreCase("Desarrollador")){
                try {
                    if (usuarioActual.getPerfilactual().equals("Administrador") || usuarioActual.getPerfilactual().equals("Desarrollador")) {
                        try {
                            if (!usuario.getPerfilesdisponibles().contains(agregarPerfil)) {
                                usuario.getPerfilesdisponibles().add(agregarPerfil);
                                usuarioRepo.save(usuario);
                            }
                        } catch (Exception e) {
                            System.out.println("El perfil habilitarPerfil ya está habilitado para el usuario");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("No tiene el perfil habilitado para realizar esta accion");
                }
            }
        } catch (Exception e){
            System.out.println("perfil no encontrado en el sistema");
        }
    }

    public usuarioEntity getUsuarioById(long idUsuario) {
        return usuarioRepo.findById(idUsuario).orElse(null);
    }

    public usuarioEntity getUsuarioByCorreo(String correo) {
        return usuarioRepo.findByCorreo(correo);
    }

    public List<String> getPerfilesDisponiblesById(long id) {

        usuarioEntity usuario = usuarioRepo.findById(id).orElse(null);

        return usuario.getPerfilesdisponibles();
    }

    public List<usuarioEntity> getAllUsuarios() {
        return usuarioRepo.findAll();
    }


    /*public void elegirPerfil(String correo, String cambioPerfil) {
        usuarioEntity usuario = usuarioRepo.findByCorreo(correo);
        usuario.setPerfilactual(cambioPerfil);
        usuarioRepo.save(usuario);

    }*/

    /*public void cambiarPerfil(String correo, String cambioPerfil) {
        try {
            usuarioEntity usuario = usuarioRepo.findByCorreo(correo);
            usuario.setPerfilactual(cambioPerfil);
            usuarioRepo.save(usuario);

        } catch (Exception e) {
            System.out.println("Usuario no encontrado con el correo");
        }
    }*/



    public void deleteUsuarioById(long idUsuario) {
        usuarioRepo.deleteById(idUsuario);
    }



    public usuarioActualEntity getUsuarioActual(){
        return usuarioActualSesion;
    }

    public long getIdUsuarioActual(){
        return usuarioActualSesion.getIdUsuario();
    }

    public String getNombreUsuarioActual(){
        return usuarioActualSesion.getNombreusuario();
    }

    public String getPerfilUsuarioActual(){
        return usuarioActualSesion.getPerfilactual();
    }

    public String getCorreoUsuarioActual(){
        return usuarioActualSesion.getCorreoUsuario();
    }

    public int anonymousLogin(){
        try {
            usuarioActualSesion = new usuarioActualEntity();
            usuarioActualSesion.setCorreoUsuario("");
            usuarioActualSesion.setPerfilactual("Anonimo");
            usuarioActualSesion.setNombreusuario("Anonimo");
            return 1;
        }
        catch (Exception e){
            return 0;
        }

    }
}


