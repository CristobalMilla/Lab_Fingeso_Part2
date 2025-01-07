<script setup>
    import { ref, onMounted } from 'vue';
    import axios from 'axios';

    const userType = ref(''); //Perfil actual 
    const isClient = ref(null); //Si es perfil cliente
    const isAdmin = ref(null); //Si es perfil administrador
    const isDeveloper = ref(null); //Si es perfil administrador
    const isEmployee = ref(null); //Si es perfil administrador
    onMounted(async () => {
        try {
            const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/perfilActual");
            userType.value = respuesta.data;
            if(userType.value == "Cliente"){
                isClient.value = true;
            }
            else if (userType.value == "Administrador"){
                isAdmin.value = true;
            }
            else if (userType.value == "Desarrollador"){
                isDeveloper.value = true;
            }
            else if (userType.value == "Empleado"){
                isEmployee.value = true;
            }
            else{
                alert("Tipo de usuario actual incorrecto");
            }
        } catch (error) {
            console.log(error);
        }
    })

</script>

<template>
    <div>EN CONSTRUCCION</div>
    <div class="alsoButtons" v-if="isAdmin">
        <router-link to="/menuAdmin">
          <div class="alsoButton" @click="regresar">Regresar al menu anterior</div>
        </router-link>
    </div>
    <div class="alsoButtons" v-if="isClient">
        <router-link to="/menuClient">
          <div class="alsoButton" @click="regresar">Regresar al menu anterior</div>
        </router-link>
    </div>
    <div class="alsoButtons" v-if="isDeveloper">
        <router-link to="/menuDeveloper">
          <div class="alsoButton" @click="regresar">Regresar al menu anterior</div>
        </router-link>
    </div>
    <div class="alsoButtons" v-if="isEmployee">
        <router-link to="/menuEmployee">
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
            async regresar(){
                //SECCION DE RETURN A MENU ANTERIOR SEGUN USUARIO ACTUAL
                try {
                    //Parte innecesaria, pero no es necesario eliminar todavia
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