<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
const sucursales = ref([]);
onMounted(async () => {
  try {
    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + 'api/sucursal/obtenerTodas');
    sucursales.value = respuesta.data;
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="add-car-container">
    <div class="add-car-card">
    <h1 class="add-car-title">Agregar un Vehículo</h1>
      <form @submit.prevent="agregarVehiculo" class="add-car-form">
        <div class="form-grid">
          <!-- Basic Info Section -->
          <div class="form-section">
            <h2 class="section-title">Información Básica</h2>

            <div class="input-group">
              <label for="brand">Marca*</label>
              <input
                  id="brand"
                  type="text"
                  v-model="vehicleBrand"
                  required
                  placeholder="ej. Toyota"
              >
            </div>
            <div class="input-group">
              <label for="model">Modelo*</label>
              <input
                  id="model"
                  type="text"
                  v-model="vehicleModel"
                  required
                  placeholder="ej. Corolla"
              >
            </div>

            <div class="input-group">
              <label for="number">Matrícula*</label>
              <input
                  id="number"
                  type="text"
                  v-model="vehicleNumber"
                  required
                  placeholder="ej. ABC123"
              >
            </div>

            <div class="input-group">
              <label for="mileage">Kilometraje*</label>
              <input
                  id="mileage"
                  type="number"
                  v-model="vehicleMilleage"
                  min="0"
                  required
                  placeholder="ej. 50000"
              >
            </div>

            <div class="input-group">
              <label for="state">Estado*</label>
              <input
                  id="state"
                  type="text"
                  v-model="vehicleState"
                  required
                  placeholder="ej. Nuevo"
              >
            </div>

            <div class="input-group">
              <label for="price">Precio Base*</label>
              <input
                  id="price"
                  type="number"
                  v-model="vehicleBasePrice"
                  min="0"
                  required
                  placeholder="ej. 15000000"
              >
            </div>

            <div class="input-group">
              <label for="sucursal">ID Sucursal*</label>
              <select id="sucursal" v-model="sucursalId" required>
                <option disabled value=""> Seleccione una sucursal </option>
                <option v-for="sucursal in sucursales" :key="sucursal.id" :value="sucursal.idsucursal">
                  {{ sucursal.nombre }}
                </option>
              </select>
            </div>
          </div>

          <div class="form-section">
            <h2 class="section-title">Detalles Técnicos</h2>

            <div class="input-group">
              <label for="category">Categoría*</label>
              <select
                  id="category"
                  v-model="vehicleCategory"
                  required
              >
                <option disabled value="">Seleccione categoría</option>
                <option value="M">M: Mini</option>
                <option value="N">N: Mini Elite</option>
                <option value="E">E: Económico</option>
                <option value="H">H: Económico Elite</option>
                <option value="C">C: Compact</option>
                <option value="D">D: Compact Elite</option>
                <option value="I">I: Intermediate</option>
                <option value="J">J: Intermediate Elite</option>
                <option value="S">S: Standard</option>
                <option value="R">R: Standard Elite</option>
                <option value="F">F: Fullsize</option>
                <option value="G">G: Fullsize Elite</option>
                <option value="P">P: Premium</option>
                <option value="U">U: Premium Elite</option>
                <option value="L">L: Lujo</option>
                <option value="W">W: Lujo Elite</option>
                <option value="O">O: Monovolúmen</option>
                <option value="X">X: Especial</option>
              </select>
            </div>

            <div class="input-group">
              <label for="type">Tipo de Vehículo*</label>
              <select
                  id="type"
                  v-model="vehicleType"
                  required
              >
                <option disabled value="">Seleccione tipo</option>
                <option value="B">B: 2-3 Puertas</option>
                <option value="C">C: 2/4 Puertas</option>
                <option value="D">D: 4-5 Puertas</option>
                <option value="W">W: Wagon/Estate</option>
                <option value="V">M: Monovolumen</option>
                <option value="L">L: Limousine</option>
                <option value="S">S: Deportivo</option>
                <option value="T">T: Descapotable</option>
                <option value="F">F: 4x4</option>
                <option value="J">J: 4x4 Abierto</option>
                <option value="X">X: Especial</option>
                <option value="P">P: Pickup cabina regular</option>
                <option value="Q">Q: Pickup grande</option>
                <option value="Z">Z: Oferta especial</option>
                <option value="E">E: Coupe</option>
                <option value="M">M: Monoespacio</option>
                <option value="R">R: Vehículo de recreación</option>
                <option value="H">H: Motor Home</option>
                <option value="Y">Y: Vehículo de 2 ruedas</option>
                <option value="N">N: Roadster deportivo 2 plazas</option>
                <option value="G">G: Crossover</option>
                <option value="K">K: Vehículo comercial</option>
              </select>
            </div>

            <div class="input-group">
              <label for="transmission">Transmisión*</label>
              <select
                  id="transmission"
                  v-model="vehicleTransmission"
                  required
              >
                <option disabled value="">Seleccione transmisión</option>
                <option value="M">Manual sin especificar tracción</option>
                <option value="N">Manual con tracción delantera</option>
                <option value="C">Manual con tracción 4 ruedas</option>
                <option value="A">Automática sin especificar tracción</option>
                <option value="B">Automático con tracción 4 ruedas</option>
                <option value="D">Automático con tracción delantera</option>
              </select>
            </div>

            <div class="input-group">
              <label for="fuel">Combustible*</label>
              <select
                  id="fuel"
                  v-model="vehiculeFuel"
                  required
              >
                <option disabled value="">Seleccione combustible</option>
                <option value="R">Combustible no especificado/Con aire</option>
                <option value="N">Combustible no especificado/Sin aire</option>
                <option value="D">Diesel Con aire</option>
                <option value="Q">Diesel Sin aire</option>
                <option value="H">Vehículo Híbrido Con aire</option>
                <option value="I">Vehículo Híbrido Sin aire</option>
                <option value="E">Eléctrico Con aire</option>
                <option value="C">Eléctrico Sin aire</option>
                <option value="L">Gas Licuado Petróleo Con aire</option>
                <option value="S">Gas Licuado Petróleo Sin aire</option>
                <option value="A">Hidrógeno Con aire</option>
                <option value="B">Hidrógeno Sin aire</option>
                <option value="M">Multicombustible Con aire</option>
                <option value="F">Multicombustible Sin aire</option>
                <option value="V">Gasolina Con aire</option>
                <option value="Z">Gasolina Sin aire</option>
                <option value="U">Etanol Con aire</option>
                <option value="X">Etanol Sin aire</option>
              </select>
            </div>


          </div>
        </div>

      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Agregar</button>
        <router-link to="/menuAdmin">
          <div class="btn btn-secondary" @click="regresar">Regresar</div>
        </router-link>
      </div>
    </form>
   </div>
  </div>
</template>
  
<script>
    import axios from 'axios';

  function redireccionarAPaginaAdministrador(){
    window.location.href = '/menuAdmin';
  }
  //Logout
  function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
  }
  export default {
    name: 'addCar',
    data(){
    return{
      brand: '',
      model: '',
      type: '',
      number: '',
      milleage: 0,
      state: '',
      basePrice: 0,
      category: '',
      transmission: '',
      fuel: '',
      photo:'',
      sucursalIds: 0,
    }
   },
    methods:{
      regresar(){
        redireccionarAPaginaAdministrador();
      },
      async agregarVehiculo(){
        //Envio de datos a backend
        console.log("holapo");
        if (true) {
          const nuevo_vehiculo = {
            "marca": this.vehicleBrand,
            "modelo": this.vehicleModel,
            "tipo": this.vehicleType,
            "matricula": this.vehicleNumber,
            "kilometraje": this.vehicleMilleage,
            "estado": this.vehicleState,
            "preciobase": this.vehicleBasePrice,
            "categoria": this.vehicleCategory,
            "transmision": this.vehicleTransmission,
            "combustible": this.vehiculeFuel,
            "fotovehiculo": "",
            "sucursal": {"idsucursal": this.sucursalId},
          }
          try {
            const registro = await axios.post(import.meta.env.VITE_BASE_URL + "api/vehiculo/crearVehiculo", nuevo_vehiculo);
            console.log(registro)
            alert("Vehiculo agregado con exito")
          } catch (error) {
            alert("No se pudo agregar con esos valores")
          }
        } else {
          alert("Faltan datos para ingresar")
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
    },
  }
</script>

<style scoped>
.add-car-container {
  min-height: 100vh;
  padding: 2rem;
  background-color: #f5f5f5;
  display: grid;
  place-items: center;
}

.add-car-card {
  max-width: 1200px;
  margin: 0 auto;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 2rem;
}

.add-car-title {
  color: #2c3e50;
  font-size: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}

.add-car-form {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.form-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.section-title {
  color: #2c3e50;
  font-size: 1.25rem;
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid #e0e0e0;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.input-group label {
  color: #4a5568;
  font-size: 0.875rem;
  font-weight: 500;
}

.input-group input,
.input-group select {
  padding: 0.75rem;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

.input-group input:focus,
.input-group select:focus {
  outline: none;
  border-color: #4299e1;
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.1);
}

.input-group input:required,
.input-group select:required {
  border-left: 3px solid #4299e1;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.btn-primary {
  background-color: #4299e1;
  color: white;
  border: none;
}

.btn-primary:hover {
  background-color: #3182ce;
}

.btn-secondary {
  background-color: #e2e8f0;
  color: #4a5568;
  border: none;
}

.btn-secondary:hover {
  background-color: #cbd5e0;
}

@media (max-width: 768px) {
  .add-car-container {
    padding: 1rem;
  }

  .add-car-card {
    padding: 1rem;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .form-actions {
    flex-direction: column;
  }

  .btn {
    width: 100%;
  }
}
</style>
