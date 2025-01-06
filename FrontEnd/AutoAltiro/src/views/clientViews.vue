<script setup>


</script>


<template>
    <main>
        <div class="general"> 
            <div class="content">
                <img class="image" src="./media/LogoUsachNegro.png">
                <div class="header">Seleccione la opcion que desee</div>
                <div class="alsoButtons">
                    <div class="inputContainers">
                        <router-link to="/availableCars">
                            <button class="sessionButton" @click="availableCars">Ver vehículos disponibles</button>
                        </router-link>
                        <router-link to="/rentCar">
                            <button class="sessionButton" @click="rentCar">Arrendar vehículo</button>
                        </router-link>
                        <router-link to="/userSelfEdit">
                            <button class="sessionButton" @click="userSelfEdit">Editar información personal</button>
                        </router-link>
                        <router-link to="/purchaseHistory">
                            <button class="sessionButton" @click="purchaseHistory">Ver Historial de Compras</button>
                        </router-link>
                        <router-link to="/tipoUsuario">
                            <div class="sessionButton" @click="regresar">Regresar</div>
                        </router-link>
                    </div>
                </div>
                <div class="alsoButtons">
                   <router-link to="/inicio">
                        <div class="alsoButton" @click="logout">Logout</div>
                   </router-link>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
    import axios from 'axios';
    //Redireccionamiento
     function go_availableCars(){
        window.location.href = '/availableCars';
    }
    
     function go_rentCar(){
        window.location.href = '/rentCar';
    }

     function go_userSelfEdit(){
        window.location.href = '/userSelfEdit';
    }

    function go_purchaseHistory(){
        window.location.href = '/purchaseHistory';
    }
    
    //Volver a pagina anterior
    function redireccionarAPaginaSeleccionTipoUsuario(){
        window.location.href = '/tipoUsuario';
    }
    //Logout
    function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
    }

    //Metodos
    export default{
        data(){
          return{
          }
        },
        methods:{
            //Redireccion a historial
            availableCars(){
                go_availableCars();
            },
            rentCar(){
                go_rentCar();
            },
            userSelfEdit(){
                go_userSelfEdit();
            },
            purchaseHistory(){
                go_purchaseHistory();
            },
            async regresar(){
                //SECCION DE RETURN A TIPO USUARIO
                try {
                    const correoActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/correo");
                    const usuarioPerfilNuevo = {
                        "correo": correoActual.data,
                        "perfilactual": "Usuario"

                    }
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/cambiarPerfil/", usuarioPerfilNuevo);
                    console.log(registro);
                    redireccionarAPaginaSeleccionTipoUsuario();
                } catch (error) {
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

<style>
.sessionButton{
	text-align: center;
}
</style>
