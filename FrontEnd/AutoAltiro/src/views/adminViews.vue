<script setup>


</script>


<template>
    <main>
        <div class="general"> 
            <div class="content">
                <img class="image" src="./media/LogoUsachNegro.png">
                <div class="header">Seleccione la opcion que desee</div>
                <div class="alsoButtons">
                    <div class="inputContainers" v-if="changeFlota">
                        <router-link to="/addCar">
                            <div class="alsoButton" @click="addCar">Agregar vehiculo a la flota</div>
                        </router-link>
                        <router-link to="/editCar">
                            <div class="alsoButton" @click="editCar">Modificar vehiculo en la flota</div>
                        </router-link>
                        <router-link to="/eraseCar">
                            <div class="alsoButton" @click="eraseCar">Eliminar un vehiculo en la flota</div>
                        </router-link>
                        <div class="alsoButton" @click="handleChangeFlota">Regresar</div>
                    </div>
                    <div class="inputContainers" v-else>
                        <div class="alsoButton" @click="handleChangeFlota">Agregar, modificar o eliminar vehiculos de la flota</div>
                    </div>
                </div>
                <div class="alsoButtons">
                    <router-link to="/recepcion">
                        <div class="alsoButton" @click="recepcion">Recepcionar vehiculos / Cambiar disponibilidad</div>
                    </router-link>
                </div>
                <div class="alsoButtons">
                   <router-link to="/tipoUsuario">
                        <div class="alsoButton" @click="returnAdmin">Regresar</div>
                   </router-link>
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
    //Records
    function redireccionarAPaginaRecords(){
        window.location.href = '/records';
    }
    //Add car
    function redireccionarAPaginaAddCar(){
        window.location.href = '/adminAddCar';
    }
    //Edit Car
    function redireccionarAPaginaEditCar(){
        window.location.href = '/adminEditUser';
    }
    //Remove Car
    function redireccionarAPaginaRemoveCar(){
        window.location.href = '/adminRemoveUser';
    }
    //Add User
    function redireccionarAPaginaAddUser(){
        window.location.href = '/adminAddUser';
    }
    //Edit User
    function redireccionarAPaginaEditUser(){
        window.location.href = '/adminEditUser';
    }
    //Remove User
    function redireccionarAPaginaRemoveUser(){
        window.location.href = '/adminRemoveUser';
    }
    //Add Branch
    function redireccionarAPaginaAddBranch(){
        window.location.href = '/adminAddBranch';
    }
    //Edit Branch
    function redireccionarAPaginaEditBranch(){
        window.location.href = '/adminEditBranch';
    }
    //Remove Branch
    function redireccionarAPaginaRemoveBranch(){
        window.location.href = '/adminRemoveBranch';
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
            changeFlota: false,
          }
        },
        methods:{
            //Redireccion a historial
            records(){
                redireccionarAPaginaRecords();
            },
            //Redireccion a menus de modificacion
            addCar(){
                redireccionarAPaginaAddCar();
            },
            editCar(){
                redireccionarAPaginaEditCar();
            },
            removeCar(){
                redireccionarAPaginaRemoveCar();
            },
            addUser(){
                redireccionarAPaginaAddUser();
            },
            editUser(){
                redireccionarAPaginaEditUser();
            },
            removeUser(){
                redireccionarAPaginaRemoveUser();
            },
            addBranch(){
                redireccionarAPaginaAddBranch();
            },
            editBranch(){
                redireccionarAPaginaEditBranch();
            },
            removeBranch(){
                redireccionarAPaginaRemoveBranch();
            },
            handleChangeFlota(){
                this.changeFlota = !this.changeFlota
                console.log(this.register)
            },
            async returnAdmin(){
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

</style>