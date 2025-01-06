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
</template>

<style>

.mensaje-error {
  color: red;
  font-weight: bold;
}

.lista-arriendos {
  margin-top: 20px;
}
</style>
