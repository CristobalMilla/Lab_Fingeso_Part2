<script setup>
// Script inicial para determinar los tipos de usuario (permisos) que el usuario logueado tendría
import axios from 'axios';
import { ref, onBeforeMount } from 'vue';

// Variables iniciales
const developer = ref(false);
const admin = ref(false);
const client = ref(false);
const employee = ref(false);
const perfiles = ref([]);
const loading = ref(true);

// Realizar antes de cargar la página
onBeforeMount(async () => {
    try {
        const idUsuario = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/id");
        const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/obtenerPerfilesById/" + idUsuario.data);
        perfiles.value = respuesta.data;
        console.log("Recuperación de datos correcta");
        if (perfiles.value.includes('Desarrollador')) {
            developer.value = true;
        }
        if (perfiles.value.includes("Administrador")) {
            admin.value = true;
        }
        if (perfiles.value.includes('Cliente')) {
            client.value = true;
        }
        if (perfiles.value.includes("Empleado")) {
            employee.value = true;
        }
    } catch (error) {
        alert(error);
    } finally {
        loading.value = false;
    }
});
</script>

<template>
  <div class="background-container" v-if="!loading">
    <img class="image" src="./media/LogoUsachBlanco.png">
    <h1>Cambiar estado de vehículos</h1>
    <router-link to="/editDelivery">
      <button @click="handleChangeEntrega">Modificar entrega</button>
    </router-link>
    <router-link to="/editAvailability">
      <button @click="handleChangeDisponibilidad">Cambiar disponibilidad vehículo</button>
    </router-link>
    <router-link to ="/tipoUsuario">
        <button @click="volver">Regresar</button>
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
            async returnClient(){
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
.background-container {
  background-color: #67c4cb;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  min-width: 175vh;
  height: 100vh;
  width: 5000;
  padding: 490px;
}

button {
  background: linear-gradient(135deg, #16acb9, #6b4cff);
  color: #f9f9f9;
  padding: 10px 20px;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  margin: 10px;
  font-size: 20px;
}

button:hover {
  background-color: #8e1a5e;
}

h1 {
color: #ffffff;
font-size: 30px;
font-style: italic;
margin: 0; 
padding: 0; 
line-height: 1; 
white-space: nowrap; 
text-align: center; 
}
</style>