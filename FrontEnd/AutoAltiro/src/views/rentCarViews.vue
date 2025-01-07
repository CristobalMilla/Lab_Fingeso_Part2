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
  <div class="page-container">
    <div class="content-card">
      <!-- Step 1: Vehicle Selection -->
      <div v-if="!vehiculoEscogido" class="step-container">
        <div class="select-wrapper">
          <label for="sucursal">Seleccione una sucursal:</label>
          <select id="sucursal" v-model="sucursalSeleccionada" class="select-input">
            <option disabled value="">-- Seleccione una sucursal --</option>
            <option v-for="sucursal in sucursales" :key="sucursal.id" :value="sucursal.idsucursal">
              {{ sucursal.nombre }}
            </option>
          </select>
          <button @click="obtenerVehiculosDisponibles" class="search-button">
            Buscar
          </button>
        </div>

        <div v-if="mensajeError" class="error-message">
          {{ mensajeError }}
        </div>

        <!-- Vehicle List -->
        <div v-if="vehiculosDisponibles.length > 0" class="vehicles-section">
          <h2 class="section-title">Vehículos Disponibles</h2>
          <div class="vehicles-grid">
            <div v-for="vehiculo in vehiculosDisponibles" :key="vehiculo.id" class="vehicle-card">
              <div class="vehicle-header">
                <h3>{{ vehiculo.marca }} {{ vehiculo.modelo }}</h3>
                <span class="status-badge">{{ vehiculo.estado }}</span>
              </div>
              <div class="vehicle-details">
                <!-- Vehicle details here -->
                <div class="detail-item">
                  <span class="detail-label">Matrícula:</span>
                  <span class="detail-value">{{ vehiculo.matricula }}</span>
                </div>
                <!-- Add other vehicle details -->
              </div>
              <button @click="escoger(vehiculo)" class="rent-button">Arrendar</button>
            </div>
          </div>
        </div>
        <div class="button-group">
          <router-link to="/menuClient" class="btn-secondary">
            Volver al Menú
          </router-link>
          <router-link to="/inicio" class="btn" @click="logout">
            Cerrar Sesión
          </router-link>
        </div>
      </div>

      <!-- Step 2: Date Selection -->
      <div v-else-if="vehiculoEscogido && !paso1Resultado" class="step-container">
        <h2>Selección de Fechas</h2>
        <div class="date-inputs">
          <div class="input-group">
            <label>Fecha de inicio:</label>
            <input type="date" v-model="fechaInicio" class="date-input">
          </div>
          <div class="input-group">
            <label>Fecha de término:</label>
            <input type="date" v-model="fechaFin" class="date-input">
          </div>
        </div>
        <div class="button-group">
          <button @click="paso1" class="btn-primary">Verificar disponibilidad</button>
          <button @click="resetProcess" class="btn-secondary">Volver al inicio</button>
        </div>
      </div>

      <!-- Step 3: Payment -->
      <div v-else-if="paso1Resultado === 1 && !paso2Resultado" class="step-container">
        <h2>Pasarela de Pago</h2>
        <div class="button-group">
          <button @click="paso2" class="btn-primary">Proceder al pago</button>
          <button @click="resetProcess" class="btn-secondary">Volver al inicio</button>
        </div>
      </div>

      <!-- Step 4: Return Branch Selection -->
      <div v-else-if="paso2Resultado == 1 && !paso3Resultado" class="step-container">
        <h2>Selección de Sucursal de Devolución</h2>
        <div class="select-wrapper">
          <label for="sucursalFinal">Sucursal de devolución:</label>
          <select id="sucursalFinal" v-model="sucursalFinal" class="select-input">
            <option disabled value="">-- Seleccione una sucursal --</option>
            <option v-for="sucursal in sucursales" :key="sucursal.id" :value="sucursal.idsucursal">
              {{ sucursal.nombre }}
            </option>
          </select>
        </div>
        <div class="button-group">
          <button v-if="sucursalFinal" @click="paso3" class="btn-primary">Finalizar arriendo</button>
          <button @click="resetProcess" class="btn-secondary">Volver al inicio</button>
        </div>
      </div>

      <!-- Success Message -->
      <div v-else-if="paso3Resultado === 1 && compraFinalizada" class="step-container">
        <h2>¡Arriendo Finalizado con Éxito!</h2>
        <div class="button-group">
          <router-link to="/menuClient" class="btn-primary">
            Volver al Menú
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default{
    data (){
        return{
            vehiculoEscogido : '',
            fechaInicio : null,
            fechaFin	: null,
            paso1Resultado : null,
            paso2Resultado : null,
            paso3Resultado : null,
            compraFinalizada : false,
            sucursalFinal: null,
        }
    },
    methods :{
        async escoger(a){
	    this.vehiculoEscogido = a;
	    console.log(this.vehiculoEscogido);
	},
      async resetProcess (){
        this.vehiculoEscogido= null;
        this.paso1Resultado = null;
        this.paso2Resultado = null;
        this.paso3Resultado = null;
        this.compraFinalizada = false;
        this.fechaInicio = '';
        this.fechaFin = '';
        this.sucursalFinal = '';
      },
        async paso1(){
            console.log(this.vehiculoEscogido);
            const arriendo = {
                "fechainicio" : this.fechaInicio,
                "fechafin" : this.fechaFin
            }
            try {
                const resultado = await axios.post(import.meta.env.VITE_BASE_URL + "api/arriendo/paso1/" + this.vehiculoEscogido.idvehiculo, arriendo);
                this.paso1Resultado = resultado.data;
                console.log("paso1Resultado :");
                console.log(this.paso1Resultado);
                if(this.paso1Resultado == 0){
                    console.log("Usuario no está logueado");
                    this.vehiculoEscogido = null;
                    alert("Usuario no está logueado");
                }
                if(this.paso1Resultado == 2){
                    console.log("No existe el vehículo en la BD");
                  this.paso1Resultado = null;
                    this.vehiculoEscogido = null;
                    alert("No existe el vehículo en la BD");

                }
                if(this.paso1Resultado == 3){
                    console.log("El vehículo no está disponible");
                  this.paso1Resultado = null;
                  this.vehiculoEscogido = null;
                    alert("El vehículo no está disponible");
                }
                if(this.paso1Resultado == 4){
                    console.log("El usuario tiene arriendos activos");
                  this.paso1Resultado = null;
                  this.vehiculoEscogido = null;
                    alert("El usuario tiene arriendos activos");
                }
                if(this.paso1Resultado == 5){
                    console.log("El vehículo no está reservado entre las fechas");
                  this.paso1Resultado = null;
                  this.vehiculoEscogido = null;
                    alert("El vehículo no está reservado entre las fechas");
                }
                if(this.paso1Resultado == 6){
                    console.log("El arriendo sobrepasa los 30 días");
                    this.paso1Resultado = null;
                    alert("El arriendo sobrepasa los 30 días");
                }
            }
            catch (error){
                alert(error);
            }
        },
        async paso2(){
            try {
                const resultado = await axios.post(import.meta.env.VITE_BASE_URL + "api/arriendo/paso2");
                this.paso2Resultado = resultado.data;
                console.log("paso2Resultado :");
                console.log(this.paso2Resultado);
            }
            catch (error){
                alert(error);
            }
        },
        async paso3(){
            const fechaCompra = {
              "fechainicio": this.fechaInicio
            }
            console.log(this.fechaInicio)
            try {
                const resultado = await axios.post(import.meta.env.VITE_BASE_URL + "api/arriendo/paso3?idSucursalFinal="+ this.sucursalFinal, fechaCompra);
                this.paso3Resultado = resultado.data;
                console.log("paso3Resultado :");
                console.log(this.paso3Resultado);
                if (this.paso3Resultado == 1){
                    this.compraFinalizada = true;
                }
            }
            catch (error){
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

<style scoped>
.page-container {
  min-height: 100vh;
  padding: 2rem;
  background: #5c6bb5;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

.content-card {
  width: 100%;
  max-width: 1200px;
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

.search-section {
  margin-bottom: 2rem;
}

.select-wrapper {
  display: flex;
  gap: 1rem;
  align-items: center;
  flex-wrap: wrap;
}

.select-wrapper label {
  color: #2c3e50;
  font-weight: 500;
}

.select-input {
  flex: 1;
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  min-width: 200px;
}

.button-group {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
  justify-content: center;
}

.btn-primary, .btn-secondary {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

.btn-primary {
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
}

.btn-secondary {
  background: #e2e8f0;
  color: #4a5568;
}

.step-container {
  padding: 2rem;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  margin-bottom: 1rem;
}

.search-button, .action-button, .rent-button {
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.search-button:hover, .action-button:hover, .rent-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
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

.vehicle-details {
  display: grid;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.date-selection {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  margin-top: 2rem;
}

.date-inputs {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  margin-bottom: 1rem;
}

.date-input {
  width: 100%;
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
}

.success-message {
  text-align: center;
  padding: 2rem;
  background: #c6f6d5;
  color: #2f855a;
  border-radius: 8px;
  margin-top: 2rem;
}

.error-message {
  color: #e53e3e;
  margin-top: 1rem;
  padding: 0.75rem;
  background: #fff5f5;
  border-radius: 8px;
  border: 1px solid #feb2b2;
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
</style>
