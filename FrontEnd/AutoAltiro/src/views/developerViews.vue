<script setup>

</script>

<template>
    <div>EN CONSTRUCCION</div>
    <div class="alsoButtons">
        <router-link to="/menuAdmin">
          <div class="alsoButton" @click="regresar">Regresar al menu anterior</div>
        </router-link>
    </div>
     <div class="alsoButtons">
        <router-link to="/inicio">
            <div class="alsoButton" @click="logout">Logout</div>
        </router-link>
    </div>
</template>

<script>
import axios from 'axios';
    //Volver a pagina anterior
    function redireccionarAPaginaSeleccionTipoUsuario(){
        window.location.href = '/tipoUsuario';
    }
    //Logout
    function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
    }
    export default {
        methods:{
            async returnDeveloper(){
                //SECCION DE RETURN A TIPO USUARIO
                try {
                    const correoActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/correo");
                    const usuarioPerfilNuevo = {
                        "correo": correoActual.data,
                        "perfilactual": "Usuario"

                    }
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/cambiarPerfil/", usuarioPerfilNuevo);
                    console.log(registro);
                    redireccionarAPaginaSeleccionTipoUsuario();
                } catch (error) {
                    alert(error);
                } 
            },
            async logout(){
                //SECCION DE LOGOUT
                try {
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/logout/");
                    console.log(registro);
                    redireccionarAPaginaPrincipal();
                } catch (error) {
                    alert(error);
                }
            }
        }
    }
</script>

<style>

</style>