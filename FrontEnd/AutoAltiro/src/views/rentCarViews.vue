<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const sucursales = ref([]); // Lista de sucursales
const sucursalSeleccionada = ref(null); // Sucursal seleccionada
const sucursalFinal = ref(null); // Sucursal seleccionada
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

    <div v-if="vehiculosDisponibles.length > 0" class="lista-vehiculos, grid">
      <h2>Vehículos disponibles:</h2>
      <ul>
        <li v-for="vehiculo in vehiculosDisponibles" :key="vehiculo.id">
          {{ vehiculo.marca }} {{ vehiculo.modelo }} - {{ vehiculo.matricula }}, Estado:{{ vehiculo.estado }}
          <button @click="escoger(vehiculo)">Arrendar</button>
        </li>
      </ul>
    </div>

    <div v-else-if="!mensajeError && sucursalSeleccionada">
      <p>No hay vehículos disponibles para esta sucursal.</p>
    </div>
    
    <div v-if="vehiculoEscogido && !compraFinalizada">
      <label>
        Escoger fecha de inicio:
          <input type="date" v-model="fechaInicio"></input>
      </label>
       <label>
        Escoger fecha de término:
          <input type="date" v-model="fechaFin"></input>
      </label>
      <button @click="paso1">Verificar disponibilidad</button>
    </div>

    <div v-if="paso1Resultado == 1 && !compraFinalizada">
      <button @click="paso2">Ir a pasarela de pago</button>
    </div>

    <div v-if="paso2Resultado == 1 && !compraFinalizada">
      <div class="selector-sucursal">
        <label for="sucursal">Seleccione una sucursal de devolución:</label>
        <select id="sucursal" v-model="sucursalFinal">
          <option disabled value="">-- Seleccione una sucursal --</option>
          <option v-for="sucursal in sucursales" :key="sucursal.id" :value="sucursal.idsucursal">
            {{ sucursal.nombre }}
          </option>
        </select>
      </div>
      <button v-if="sucursalFinal" @click="paso3">Finalizar compra</button>
    </div>

    <div v-if="paso3Resultado == 1 && compraFinalizada">
      <p>¡Compra Finalizada!</p>
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
        }
    },
    methods :{
        async escoger(a){
	    this.vehiculoEscogido = a;
	    console.log(this.vehiculoEscogido);
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
            }
            catch (error){
                alert(error);
            }
        },
        async paso2(){
            try {
                const resultado = await axios.post(import.meta.env.VITE_BASE_URL + "api/arriendo/paso2/", null);
                this.paso2Resultado = resultado.data;
                console.log("paso2Resultado :");
                console.log(this.paso2Resultado);
            }
            catch (error){
                alert(error);
            }
        },
        async paso3(){
            const fechaCompra = fechaInicio.value;
            try {
                const resultado = await axios.post(import.meta.env.VITE_BASE_URL + "api/arriendo/paso3?idSucursalFinal="+ sucursalFinal.value, fechaCompra);
                this.paso3Resultado = resultado.data;
                console.log("paso3Resultado :");
                console.log(this.paso3Resultado);
                if (paso3Resultado == 1){
                    this.compraFinalizada = true;
                }
            }
            catch (error){
                alert(error);
            }
        }
    }
}
</script>

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

.grid {
  display: grid;
  grid-gap: 12px;
  grid-template-columns: repeat(4, 400px);
}
</style>
