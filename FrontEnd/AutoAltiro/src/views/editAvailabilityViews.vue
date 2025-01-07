<script setup>
import axios from 'axios';
import { ref, onBeforeMount } from 'vue';

const availableVehicles = ref([]);
const loadingVehicles = ref(true);

const fetchAvailableVehicles = async () => {
    try {
        const response = await axios.get(import.meta.env.VITE_BASE_URL + "api/vehiculo/obtenerTodos");
        availableVehicles.value = response.data;
        console.log("Vehículos disponibles recuperados:", availableVehicles.value);
    } catch (error) {
        alert("Error al recuperar vehículos disponibles: " + error.message);
    } finally {
        loadingVehicles.value = false;
    }
};

const updateEstado = async (vehiculo) => {
    try {
      const response = await axios.put(import.meta.env.VITE_BASE_URL + "api/vehiculo/cambiarDisponibilidad", null, {
        params: {
          idVehiculo: vehiculo.idvehiculo,
          estado: vehiculo.nuevoEstado,
        },
      });

      vehiculo.estado = response.data.estado;
      vehiculo.nuevoEstado = "";
      alert(`Estado del vehículo ${vehiculo.idvehiculo} actualizado correctamente.`);
    } catch (error) {
      alert("Error al actualizar el estado del vehículo");
    }
};


onBeforeMount(async () => {
    try {
        await fetchAvailableVehicles();
    } catch (error) {
        console.error("Error en onBeforeMount:", error.message);
    }
});
</script>

<template>
    <h1>Hola</h1>
    <h3>Vehículos activos</h3>
    <ul>
      <li v-for="vehiculo in availableVehicles" :key="vehiculo.idvehiculo" class="vehicle-item">
        <div class="vehicle-details">
          <span class="vehicle-info id">Id: {{ vehiculo.idvehiculo }}</span>
          <span class="vehicle-info marca">Marca: {{ vehiculo.marca }}</span>
          <span class="vehicle-info estado">Estado: 
            <select v-model="vehiculo.nuevoEstado">
              <option disabled value="">Seleccionar nuevo estado</option>
              <option value="Disponible">Disponible</option>
              <option value="Ocupado">Ocupado</option>
              <option value="Mantenimiento">Mantenimiento</option>
            </select>
          </span>
          <button @click="updateEstado(vehiculo)">Actualizar</button>
        </div>
      </li>
    </ul>
  </template>

  <style>
  ul {
    list-style: none;
    padding: 0;
  }
  
  .vehicle-item {
    background: linear-gradient(135deg, #2447bd, #29c9ce);
    margin-bottom: 10px;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0px 4px 6px rgba(255, 255, 255, 0.1);
  }
  
  .vehicle-details {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  
  .vehicle-info {
    padding: 5px 20px;
    border-radius: 20px;
    color: rgb(255, 255, 255);
    font-weight: bold;
  }
  
  button {
    margin-left: auto;
    padding: 8px 16px;
    background: linear-gradient(135deg, #16acb9, #6b4cff);
    color: white;
    border: none;
    border-radius: 6px;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  button:hover {
    background: linear-gradient(135deg, #16acb9, #6b4cff);
    transform: scale(1.05);
  }
  </style>