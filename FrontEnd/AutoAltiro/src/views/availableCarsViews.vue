<script setup>
import axios from 'axios';
import {ref, onBeforeMount, onMounted} from 'vue';

const sucursales = ref([]); // Lista de sucursales
const sucursalSeleccionada = ref(null); // Sucursal seleccionada
const vehiculosDisponibles = ref([]); // Lista de vehículos disponibles
const mensajeError = ref(''); // Mensaje de error
const userType = ref(''); //Perfil actual
const isClient = ref(null); //Si es perfil cliente
const isAnon = ref(null); //Si es perfil anonimo

const formatPrice = (price) => {
  return new Intl.NumberFormat('es-CL').format(price);
};

const getStatusClass = (status) => {
  switch (status.toLowerCase()) {
    case 'disponible':
      return 'status-available';
    case 'ocupado':
      return 'status-occupied';
    case 'mantenimiento':
      return 'status-maintenance';
    default:
      return '';
  }
};

onMounted(async () => {
  try {
    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + 'api/sucursal/obtenerTodas');
    sucursales.value = respuesta.data;
  } catch (error) {
    mensajeError.value = 'Error al cargar las sucursales.';
    console.error(error);
  }
});
onMounted(async () => {
  try {
    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/perfilActual");
    userType.value = respuesta.data;
    if(userType.value == "Cliente"){
      isClient.value = true;
    }
    if (userType.value == "Anonimo"){
      isAnon.value = true;
    }
    else{
      alert("Tipo de usuario actual incorrecto");
    }
  } catch (error) {
    console.log(error);
  }
})
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
  <div class="page-container">
    <div class="content-card">
      <h1 class="page-title">Vehículos Disponibles por Sucursal</h1>
      <div class="button-group">
        <router-link to="/menuClient" class="btn">
          Volver al Menú
        </router-link>
        <router-link to="/inicio" class="btn" @click="logout">
          Cerrar Sesión
        </router-link>
      </div>
      <div class="search-section">
        <div class="select-wrapper">
          <label for="sucursal">Seleccione una sucursal:</label>
          <select
              id="sucursal"
              v-model="sucursalSeleccionada"
              class="select-input"
          >
            <option disabled value="">-- Seleccione una sucursal --</option>
            <option
                v-for="sucursal in sucursales"
                :key="sucursal.id"
                :value="sucursal.idsucursal"
            >
              {{ sucursal.nombre }}
            </option>
          </select>
          <button
              @click="obtenerVehiculosDisponibles"
              class="search-button"
          >
            Buscar
          </button>
        </div>

        <div v-if="mensajeError" class="error-message">
          {{ mensajeError }}
        </div>
      </div>

      <div v-if="vehiculosDisponibles.length > 0" class="vehicles-section">
        <h2 class="section-title">Vehículos Disponibles</h2>
        <div class="vehicles-grid">
          <div
              v-for="vehiculo in vehiculosDisponibles"
              :key="vehiculo.id"
              class="vehicle-card"
          >
            <div class="vehicle-header">
              <h3>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h3>
              <span class="status-badge" :class="getStatusClass(vehiculo.estado)">
                {{ vehiculo.estado }}
              </span>
            </div>
            <div class="vehicle-details">
              <div class="detail-item">
                <span class="detail-label">Tipo:</span>
                <span class="detail-value">{{ vehiculo.tipo }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Matrícula:</span>
                <span class="detail-value">{{ vehiculo.matricula }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Kilometraje:</span>
                <span class="detail-value">{{ vehiculo.kilometraje }} km</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Precio Base:</span>
                <span class="detail-value">${{ formatPrice(vehiculo.preciobase) }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Categoría:</span>
                <span class="detail-value">{{ vehiculo.categoria }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Transmisión:</span>
                <span class="detail-value">{{ vehiculo.transmision }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Combustible:</span>
                <span class="detail-value">{{ vehiculo.combustible }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div
          v-else-if="!mensajeError && sucursalSeleccionada"
          class="no-results"
      >
        <p>No hay vehículos disponibles para esta sucursal.</p>
      </div>
    </div>
  </div>
</template>
<script>
function redireccionarAPaginaCliente(){
  window.location.href = '/menuClient';
}
//Logout
function redireccionarAPaginaPrincipal(){
  window.location.href = '/inicio';
}
export default{
  methods:{
    regresar(){
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


<style scoped>

.page-title {
  color: #2c3e50;
  font-size: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}

.search-section {
  margin-bottom: 2rem;
}

.select-wrapper {
  display: flex;
  gap: 1rem;
  align-items: center;
  flex-wrap: wrap;
}

.select-input {
  flex: 1;
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  min-width: 200px;
}

.search-button {
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.search-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.error-message {
  color: #e53e3e;
  margin-top: 1rem;
  padding: 0.75rem;
  background: #fff5f5;
  border-radius: 8px;
  border: 1px solid #feb2b2;
}

.vehicles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-top: 1.5rem;
}

.vehicle-card {
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  border-radius: 12px;
  padding: 1.5rem;
  color: white;
}

.vehicle-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.vehicle-header h3 {
  margin: 0;
  font-size: 1.25rem;
}

.vehicle-details {
  display: grid;
  gap: 0.75rem;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-label {
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.875rem;
}

.detail-value {
  font-weight: 500;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  font-size: 0.875rem;
  font-weight: 500;
  background: rgba(255, 255, 255, 0.2);
}

.no-results {
  text-align: center;
  padding: 2rem;
  background: #f8fafc;
  border-radius: 8px;
  color: #64748b;
}

@media (max-width: 768px) {
  .page-container {
    padding: 1rem;
  }

  .content-card {
    border-radius: 0;
    padding: 1rem;
  }

  .select-wrapper {
    flex-direction: column;
  }

  .select-input {
    width: 100%;
  }

  .search-button {
    width: 100%;
  }
}
.button-group {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
  justify-content: center;
}
</style>