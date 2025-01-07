<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const arriendos = ref([]); // Lista de sucursales
const mensajeError = ref(''); // Mensaje de error

// Cargar los arriendos al montar el componente
onMounted(async () => {
  try {
    const user = JSON.parse(localStorage.getItem("usuario"));
    console.log(user);
    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + 'api/arriendo/obtenerPorCliente/'+ user.idusuario);
    arriendos.value = respuesta.data;
  } catch (error) {
    mensajeError.value = 'Error al cargar los arriendos.';
    console.error(error);
  }
});
</script>

<template>
  <div class="pagina-sucursales">
    <h1>Historial de compras</h1>

    <div v-if="arriendos.length > 0" class="lista-arriendos">
      <h2>Arriendos:</h2>
        <ul v-for="arriendo in arriendos" :key="arriendo.id">
          <li>Fecha inicio-término:{{ arriendo.fechainicio }}-{{ arriendo.fechafin}}</li>
          <li>Vehículo:{{ arriendo.vehiculo }}</li>
          <li>Sucursal recogida: {{ arriendo.sucursalrecogida }}</li>
          <li>Sucursal devolución:  {{ arriendo.sucursaldevolucion }} </li>
          <li>Comprobante: {{arriendo.comprobante}}</li>
        </ul>
    </div>

    <div v-if="mensajeError" class="mensaje-error">
      {{ mensajeError }}
    </div>

  </div>
  <router-link to ="/menuClient">
        <button @click="volver">Regresar</button>
    </router-link>
    <div class="alsoButtons">
      <router-link to="/inicio">
        <div class="alsoButton" @click="logout">Logout</div>
      </router-link>
    </div>
</template>

<script>
  import axios from 'axios';
  function redireccionarAPaginaCliente(){
    window.location.href = '/menuClient';
  }
  //Logout
  function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
  }
  export default{
    methods:{
      volver(){
                redireccionarAPaginaCliente();
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

.mensaje-error {
  color: red;
  font-weight: bold;
}

.lista-arriendos {
  margin-top: 20px;
}
</style>
