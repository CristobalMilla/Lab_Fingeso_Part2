<script setup>
import axios from 'axios';
import { ref } from 'vue';

const arriendoId = ref("");
const selectedLease = ref(null);
const loadingLeases = ref(false);

const fetchLeaseById = async () => {
    loadingLeases.value = true;
    try {
        const response = await axios.get(import.meta.env.VITE_BASE_URL + "api/arriendo/obtenerArriendo/" + arriendoId.value);
        selectedLease.value = response.data;
        console.log("selectedLease:", selectedLease.value);
    } catch (error) {
        alert("Hubo un error al buscar el arriendo.");
    } finally {
        loadingLeases.value = false;
    }
};

const updateEstado = async () => {
    console.log("selectedLease:", selectedLease.value);
    const lease = selectedLease.value
    console.log(lease.idarriendo)
    try {
      const response = await axios.put(import.meta.env.VITE_BASE_URL + "api/arriendo/cambiarEstado/" + lease.idarriendo + "?nuevoEstado=" + 
      lease.nuevoEstado 
    );

        lease.estado = response.data.estado; 
        lease.nuevoEstado = "";
        alert("Estado del arriendo actualizado correctamente.");
    } catch (error) {
        alert(error);
    }
};
</script>

<template>
    <h1>Gestión de Arriendos</h1>
    <h2>Buscar Arriendo</h2>
    <div>
        <input v-model="arriendoId" placeholder="Ingrese el ID del arriendo" />
        <button @click="fetchLeaseById">Buscar</button>
    </div>

    <div v-if="selectedLease" class="selectedLease-details">
        <h4>Detalles del Arriendo</h4>
        <p><strong>Estado Actual:</strong> {{ selectedLease.estado }}</p>
        <span class="lease-info estado">
            Estado:
            <select v-model="selectedLease.nuevoEstado">
                <option disabled value="">Seleccionar nuevo estado</option>
                <option value="en uso">En uso</option>
                <option value="retirar">Retirar</option>
                <option value="terminado">Terminado</option>
            </select>
            <button @click="updateEstado">Actualizar</button>
        </span>
    </div>
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
}

button:hover {
  background: linear-gradient(135deg, #16acb9, #6b4cff);
}
</style>