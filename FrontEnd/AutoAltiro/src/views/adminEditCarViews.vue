<script setup>

</script>

<template>
    <div class="modifyCar">
    <h1>Modificar un vehiculo</h1>
    <form>
      <div class="InputContainer">
        <input type="number" v-model="vehicleId" min=0 placeholder="Ingrese el id del vehiculo">
        <input type="marca" v-model="vehicleBrand" placeholder="Ingrese la marca del vehiculo">
        <input type="modelo" v-model="vehicleModel" placeholder="Ingrese el modelo del vehiculo">
        <select v-model="vehicleType">
            <option disabled value="">Ingrese tipo de vehículo</option>
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
        <input type="matricula" v-model="vehicleNumber" placeholder="Ingrese el numero de matricula  del vehiculo">
        <input type="number" v-model="vehicleMilleage" min=0 placeholder="Ingrese el kilometraje del vehiculo">
        <input type="estado" v-model="vehicleState" placeholder="Ingrese el estado del vehiculo">
        <input type="number" v-model="vehicleBasePrice" min=0 placeholder="Ingrese el precio base del vehiculo">
        <select v-model="vehicleCategory">
            <option disabled value="">Ingrese categoría de vehículo</option>
            <option value="M">M: Mini</option>
            <option value="N">N: Mini Elite</option>
            <option value="E">E: Económico</option>
            <option value="H">H: Económico Elite</option>
            <option value="C">C: Compact</option>
            <option value="D">D: Compact Elite</option>
            <option value="I">I: Intermediate</option>
            <option value="J">J: Intermediate Elite</option>
            <option value="S">S: Standard </option>
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
        <select v-model="vehicleTransmission">
            <option disabled value="">Ingrese tipo de transmisión del vehículo</option>
            <option value="M">M: Manual sin especificar tracción</option>
            <option value="N">N: Manual con tracción delantera</option>
            <option value="C">C: Manual con tracción 4 ruedas</option>
            <option value="A">A: Automática sin especificar tracción</option>
            <option value="B">B: Automático con tracción 4 ruedas</option>
            <option value="D">D: Automático con tracción delantera</option>
        </select>
        <select v-model="vehiculeFuel">
            <option disabled value="">Ingrese tipo de combustible del vehículo</option>
            <option value="R">R: Combustible no especificado/Con aire</option>
            <option value="N">N: Combustible no especificado/Sin aire</option>
            <option value="D">D: Diesel Con aire</option>
            <option value="Q">Q: Diesel Sin aire</option>
            <option value="H">H: Vehículo Híbrido Con aire</option>
            <option value="I">I: Vehículo Híbrido Sin aire</option>
            <option value="E">E: Eléctrico Con aire</option>
            <option value="C">C: Eléctrico Sin aire</option>
            <option value="L">L: Gas Licuado Petróleo Con aire</option>
            <option value="S">S: Gas Licuado Petróleo Sin aire</option>
            <option value="A">A: Hidrógeno Con aire</option>
            <option value="B">B: Hidrógeno Sin aire</option>
            <option value="M">M: Multicombustible Con aire</option>
            <option value="F">F: Multicombustible Sin aire</option>
            <option value="V">V: Gasolina Con aire</option>
            <option value="Z">Z: Gasolina Sin aire</option>
            <option value="U">U: Etanol Con aire</option>
            <option value="X">X: Etanol Sin aire</option>
        </select>
        <input type="number" v-model="sucursalId" min=1 placeholder="Ingrese el numero de Id de la sucursal">
        <button class="sessionButton" @click="modificarVehiculo">modificar vehiculo</button>
      </div>
      <div class="alsoButtons">
        <router-link to="/menuAdmin">
          <div class="alsoButton" @click="regresar">Regresar al menu anterior</div>
        </router-link>
      </div>
    </form>
  </div>
</template>


<script>
    import axios from 'axios';
    function redireccionarAPaginaAdministrador(){
        window.location.href = '/menuAdmin';
    }
    //Lougout
    function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
    }
    export default {
        name: 'ModifyCar',
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
            async modificarVehiculo(){
                //Envio de datos a backend
                if (this.vehicleBrand != "" || this.vehicleModel != "" || this.vehicleType != "" || this.vehiculeNumber != "" || this.vehicleMilleage != "" || this.vehicleState != "" || vehicleBasePrice != "" || vehicleCategory != "" || vehicleTransmission != "" || this.vehiculeFuel != "" || this.sucursalId != "") {
                    const nuevo_vehiculo = {
                        "idvehiculo": this.vehicleId,
                        "marca": this.vehicleBrand,
                        "modelo": this.vehicleModel,
                        "tipo": this.vehicleType,
                        "matricula": this.vehicleNumber,
                        "kilometraje": this.vehicleMilleage,
                        "estado": this.vehicleState,
                        "preciobase": this.vehicleBasePrice,
                        "categoria": this.vehicleCategory,
                        "transmision": this.vehicleTransmission,
                        "combustible": this.vehicleFuel,
                        "fotovehiculo": "",
                        "sucursal": {"idsucursal": this.sucursalId},
                    }
                    console.log('idvehiculo:', this.idVehiculo, typeof this.idVehiculo);
                    console.log('sucursalId:', this.sucursalId, typeof this.sucursalId);
                    try {
                        const respuesta = await axios.put(import.meta.env.VITE_BASE_URL + "api/vehiculo/actualizarVehiculo/" + this.vehicleId, nuevo_vehiculo);
                        if(respuesta.data == 1){
                            alert("Vehiculo modificado con exito");
                        }
                        if(respuesta.data == 0){
                            alert("El id de vehiculo o los atributos son incorrectos");
                        }
                        respuesta.data = 0;
                        console.log(respuesta);
                    } catch (error) {
                        alert("No se genera conexion con el servidor")
                    }           
                } else {
                    alert("Debe llenar al menos uno de los campos")
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

<style>
input:required {
  border-color: #800000;
  border-width: 3px;
}

input:required:invalid {
  border-color: #c00000;
}
input:required:valid {
  border-color: #00c000;
}
</style>
