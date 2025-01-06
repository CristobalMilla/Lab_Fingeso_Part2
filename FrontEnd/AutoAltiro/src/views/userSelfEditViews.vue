<script setup>

</script>

<template>


</template>

<script>
    import axios from 'axios';
    //Volver a pagina anterior
    function redireccionarAPaginaAdministrador(){
        window.location.href = '/menuAdmin';
    }
    function redireccionarAPaginaCliente(){
        window.location.href = '/menuClient';
    }
    function redireccionarAPaginaDesarrollador(){
        window.location.href = '/menuDeveloper';
    }
    function RedireccionarAPaginaMenuEmpleado(){
        window.location.href = '/menuEmployee';
    }
    //Lougout
    function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
    }
    export default {
        methods:{
            async return(){
                //SECCION DE RETURN A MENU ANTERIOR SEGUN USUARIO ACTUAL
                try {
                    const perfilActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/perfilActual");
                    console.log(perfilActual);
                    if (perfilActual.data == "Administrador"){
                        redireccionarAPaginaAdministrador();
                    }
                    else if (perfilActual.data == "Cliente"){
                        redireccionarAPaginaCliente();
                    }
                    else if (perfilActual.data == "Empleado"){
                        RedireccionarAPaginaMenuEmpleado();
                    }
                    else if (perfilActual.data == "Desarrollador"){
                        redireccionarAPaginaDesarrollador();
                    }
                    else {
                        alert("error con el tipo de usuario actual");
                    }
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