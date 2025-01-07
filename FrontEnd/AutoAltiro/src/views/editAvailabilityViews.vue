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
  <div class="availability-container">
    <div class="availability-card">
      <h1 class="availability-title">Gestión de Disponibilidad de Vehículos</h1>
      <div class="action-buttons">
        <router-link to="/menuEmployee" class="return-btn">
          Regresar
        </router-link>
      </div>
      <div class="vehicles-list">
        <div v-for="vehiculo in availableVehicles"
             :key="vehiculo.idvehiculo"
             class="vehicle-item">
          <div class="vehicle-details">
            <div class="vehicle-info">
              <span class="info-label">ID:</span>
              <span class="info-value">{{ vehiculo.idvehiculo }}</span>
            </div>
            <div class="vehicle-info">
              <span class="info-label">Marca:</span>
              <span class="info-value">{{ vehiculo.marca }}</span>
            </div>
            <div class="vehicle-info">
              <span class="info-label">Estado:</span>
              <select v-model="vehiculo.nuevoEstado" class="status-select">
                <option disabled value="">Seleccionar nuevo estado</option>
                <option value="Disponible">Disponible</option>
                <option value="Ocupado">Ocupado</option>
                <option value="Mantenimiento">Mantenimiento</option>
              </select>
            </div>
            <button class="update-btn" @click="updateEstado(vehiculo)">
              Actualizar
            </button>
          </div>
        </div>
      </div>


    </div>
  </div>
</template>

<style scoped>
.availability-container {
  min-height: 100vh;
  padding: 2rem;
  background: #5c6bb5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.availability-card {
  width: 100%;
  max-width: 1200px;
  margin: 1rem;
  background: #a9b7fc;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  padding: 2rem;
  overflow-y: auto;
  max-height: calc(100vh - 4rem);
}

.availability-title {
  color:white;
  font-size: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}

.vehicles-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.vehicle-item {
  background: linear-gradient(135deg, #16acb9, #6b4cff);
  padding: 1.5rem;
  border-radius: 12px;
  color: white;
}

.vehicle-details {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.vehicle-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.info-label {
  font-weight: 500;
  color: rgba(255, 255, 255, 0.9);
}

.info-value {
  font-weight: 600;
}

.status-select {
  padding: 0.5rem;
  border-radius: 6px;
  border: none;
  background: white;
  color: #2c3e50;
  font-size: 0.9rem;
}

.update-btn {
  margin-left: auto;
  padding: 0.5rem 1rem;
  background: white;
  color: #2447bd;
  border: none;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.update-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.action-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 2rem;
}

.return-btn {
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
  border: none;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s;
  text-align: center;
}

.return-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .availability-container {
    padding: 1rem;
  }

  .availability-card {
    margin: 0;
    border-radius: 0;
    padding: 1rem;
  }

  .vehicle-details {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
  }

  .update-btn {
    width: 100%;
    margin: 0;
  }
}
</style>