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
  <div class="page-container">
    <div class="content-card">
      <h1 class="page-title">Gestión de Arriendos</h1>

      <div class="search-section">
        <h2 class="section-title">Buscar Arriendo</h2>
        <div class="search-container">
          <input
              v-model="arriendoId"
              placeholder="Ingrese el ID del arriendo"
              class="search-input"
          />
          <button @click="fetchLeaseById" class="search-button">
            Buscar
          </button>
        </div>
      </div>

      <div v-if="selectedLease" class="lease-details">
        <h3 class="details-title">Detalles del Arriendo</h3>
        <div class="status-container">
          <p class="current-status">
            <strong>Estado Actual:</strong>
            <span class="status-badge">{{ selectedLease.estado }}</span>
          </p>
          <div class="status-update">
            <select
                v-model="selectedLease.nuevoEstado"
                class="status-select"
            >
              <option disabled value="">Seleccionar nuevo estado</option>
              <option value="en uso">En uso</option>
              <option value="retirar">Retirar</option>
              <option value="terminado">Terminado</option>
            </select>
            <button @click="updateEstado" class="update-button">
              Actualizar
            </button>
          </div>
        </div>
      </div>

      <div class="action-buttons">
        <router-link to="/menuEmployee" class="btn btn-primary">
          Regresar
        </router-link>
        <router-link to="/inicio" class="btn btn-danger" @click="logout">
          Cerrar Sesión
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  function redireccionarAPaginaEmpleado(){
    window.location.href = '/menuEmployee';
  }
  //Logout
  function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
  }
  export default{
    methods:{
      volver(){
                redireccionarAPaginaEmpleado();
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
.page-container {
  min-height: 100vh;
  padding: 2rem;
  background: #5c6bb5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content-card {
  width: 100%;
  max-width: 800px;
  background: #a9b7fc;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  padding: 2rem;
}

.page-title {
  color: #2c3e50;
  font-size: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}

.section-title {
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.search-section {
  margin-bottom: 2rem;
}

.search-container {
  display: flex;
  gap: 1rem;
}

.search-input {
  flex: 1;
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
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

.lease-details {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 2rem;
}

.details-title {
  color: #2c3e50;
  font-size: 1.25rem;
  margin-bottom: 1rem;
}

.status-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.current-status {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  background: #e2e8f0;
  border-radius: 9999px;
  font-size: 0.875rem;
}

.status-update {
  display: flex;
  gap: 1rem;
}

.status-select {
  flex: 1;
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
}

.update-button {
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.action-buttons {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  text-decoration: none;
  transition: all 0.2s;
  text-align: center;
}

.btn-primary {
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
}

.btn-danger {
  background: linear-gradient(135deg, #e53e3e, #c53030);
  color: white;
}

.btn:hover, .search-button:hover, .update-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .page-container {
    padding: 1rem;
  }

  .content-card {
    border-radius: 0;
    padding: 1rem;
  }

  .search-container, .status-update {
    flex-direction: column;
  }

  .action-buttons {
    flex-direction: column;
  }

  .btn {
    width: 100%;
  }
}
</style>