<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const sucursales = ref([]); // Lista de sucursales
const sucursalSeleccionada = ref(null); // Sucursal seleccionada
const vehiculosDisponibles = ref([]); // Lista de vehículos disponibles
const mensajeError = ref(''); // Mensaje de error

// Cargar las sucursales al montar el componente
onMounted(async () => {
  try {
    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + 'api/sucursal/obtenerTodas');
    sucursales.value = respuesta.data;
  } catch (error) {
    mensajeError.value = 'Error al cargar las sucursales.';
    console.error(error);
  }
});

// Obtener los vehículos disponibles para la sucursal seleccionada
const obtenerVehiculosDisponibles = async () => {
  if (!sucursalSeleccionada) {
    mensajeError.value = 'Por favor seleccione una sucursal.';
    return;
  }

  try {
  console.log(sucursalSeleccionada);
    const respuesta = await axios.get(
      import.meta.env.VITE_BASE_URL + 'api/vehiculo/disponibles/' + sucursalSeleccionada.value
    );
    vehiculosDisponibles.value = respuesta.data;
    mensajeError.value = '';
  } catch (error) {
    mensajeError.value = 'Error al obtener los vehículos disponibles.';
    console.error(error);
  }
};
</script>

<template>
  <div class="pagina-sucursales">
    <h1>Vehículos disponibles por sucursal</h1>

    <div class="selector-sucursal">
      <label for="sucursal">Seleccione una sucursal:</label>
      <select id="sucursal" v-model="sucursalSeleccionada">
        <option disabled value="">-- Seleccione una sucursal --</option>
        <option v-for="sucursal in sucursales" :key="sucursal.id" :value="sucursal.idsucursal">
          {{ sucursal.nombre }}
        </option>
      </select>
      <button @click="obtenerVehiculosDisponibles">Buscar</button>
    </div>

    <div v-if="mensajeError" class="mensaje-error">
      {{ mensajeError }}
    </div>

    <div v-if="vehiculosDisponibles.length > 0" class="lista-vehiculos">
      <h2>Vehículos disponibles:</h2>
      <div class="grid">
        <div v-for="vehiculo in vehiculosDisponibles" :key="vehiculo.id">
          <ul>
            <li>{{ vehiculo.marca }} - {{ vehiculo.modelo }}</li>
            <li> Tipo: {{vehiculo.tipo}}</li>
            <li> Matrícula : {{ vehiculo.matricula }}</li>
            <li> Kilometraje: {{ vehiculo.kilometraje }}</li>
            <li> Precio Base: {{ vehiculo.preciobase }}</li>
            <li> Categoría: {{ vehiculo.categoria }}</li>
            <li> Transmision: {{ vehiculo.transmision }}</li>
            <li> Combustible: {{ vehiculo.combustible }}</li>
            <li> Estado:{{ vehiculo.estado }}</li>
          </ul>
        </div>
      </div>
    </div>

    <div v-else-if="!mensajeError && sucursalSeleccionada">
      <p>No hay vehículos disponibles para esta sucursal.</p>
    </div>
  </div>
</template>

<style>
.pagina-sucursales {
  font-family: Arial, sans-serif;
  margin: 20px;
}

.selector-sucursal {
  margin-bottom: 20px;
}

.mensaje-error {
  color: red;
  font-weight: bold;
}

.lista-vehiculos {
  margin-top: 20px;
}

.grid{
  display: grid;
  grid-gap: 30px;
  //grid-template-columns: repeat(2);
}
</style>
