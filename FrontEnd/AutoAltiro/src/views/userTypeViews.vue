<script setup>
    //Script inicial para determinar los tipos de usuario (permisos) que el usuario logueado tendria
    import axios from 'axios';
    import {ref, onBeforeMount} from 'vue';
    //Variables iniciales
    const developer = ref(false);
    const admin = ref(false);
    const client = ref(false);
    const employee = ref(false);
    const perfiles = ref([]);
    const loading = ref(true);
    //Realizar antes de cargar la pagina
    onBeforeMount(async ()=> {
        try {
            //Recuperar el id del usuario actual
            const idUsuario = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/id");
            //Recuperar la lista de tipos de usuario del usuario actual segun el id recuperado
            const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/obtenerPerfilesById/" + idUsuario.data);
            //Asignar lo recuperado a la variable
            perfiles.value = respuesta.data;
            console.log("Recuperacion de datos correcta");
            //Seccion para cambiar variables segun los tipos de usuario encontrados
            if(perfiles.value.includes('Desarrollador')){
                developer.value = true;
            };
            if(perfiles.value.includes("Administrador")){
                admin.value = true;
            };
            if(perfiles.value.includes('Cliente')){
                client.value = true;
                console.log(client);
            };
            if(perfiles.value.includes("empleado")){
                employee.value = true;
            };
        } catch (error) {
            alert(error);
        } finally {
            //Cambiar la variable de carga de pagina
            loading.value=false;
        }
    });
</script>

<template>
   <main>
    <div class="general">
        <div class="content">
            <img class="image" src="./media/LogoUsachNegro.png">
            <div class="header">Elija el tipo de usuario que desea utilizar</div>
            <div class="inputContainers" v-if="developer">
                <router-link to ='/desarrollador'>
                    <div class="alsoButton" @click="menuDeveloper">Ingresar como desarrollador</div>
                </router-link>
            </div>
            <div class="inputContainers" v-if="admin">
                <router-link to ='/administrador'>
                    <div class="alsoButton" @click="menuAdmin">Ingresar como administrador</div>
                </router-link>
            </div>
            <div class="inputContainers" v-if="client">
                <router-link to ='/cliente'>
                    <div class="alsoButton" @click="menuClient">Ingresar como cliente</div>
                </router-link>
            </div>
            <div class="inputContainers" v-if="employee">
                <router-link to ='/empleado'>
                    <div class="alsoButton" @click="menuEmployee">Ingresar como empleado</div>
                </router-link>
            </div>
            <div class="headerDescription" v-if="!developer && !admin && !client && !employee">Su usuario no tiene los permisos correctos</div>
            <div class="alsoButtons">
                <div class="alsoButton" @click="logout">Logout</div>
            </div>
        </div>
    </div>
   </main>
</template>


<script>
    import axios from 'axios';
    //Redireccionamientos
    //Menu Developer
    function RedireccionarAPaginaMenuDeveloper(){
        window.location.href = '/menuDeveloper';
    }
    //Menu Admin
    function redireccionarAPaginaMenuAdmin(){
        window.location.href = '/menuAdmin';
    }
    //Menu Client
    function RedireccionarAPaginaMenuClient(){
        window.location.href = '/menuClient';
    }
    //Menu Employee
    function RedireccionarAPaginaMenuEmployee(){
        window.location.href = '/menuEmployee';
    }
    //Lougout
    function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
    }
    export default{
        methods:{
           //Metodos de redireccionamiento
           //SECCION CAMBIO DE PERFIL ACTUAL
            async menuDeveloper(){
                try {
                    const correoActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/correo");
                    const usuarioPerfilNuevo = {
                        "correo": correoActual.data,
                        "perfilactual": "Desarrollador"
                    }
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/cambiarPerfil/", usuarioPerfilNuevo);
                    console.log(registro);
                    RedireccionarAPaginaMenuDeveloper();
                } catch (error) {
                    alert(error);
                }
            },
            async menuAdmin(){
                try {
                    const correoActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/correo");
                    const usuarioPerfilNuevo = {
                        "correo": correoActual.data,
                        "perfilactual": "Administrador"
                    }
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/cambiarPerfil/", usuarioPerfilNuevo);
                    console.log(registro);
                    redireccionarAPaginaMenuAdmin();
                } catch (error) {
                    alert(error);
                }
            },
            async menuClient(){
                try {
                    const correoActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/correo");
                    const usuarioPerfilNuevo = {
                        "correo": correoActual.data,
                        "perfilactual": "Cliente"
                    }
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/cambiarPerfil/", usuarioPerfilNuevo);
                    console.log(registro);
                    RedireccionarAPaginaMenuClient();
                } catch (error) {
                    alert(error);
                }
            },
            async menuEmployee(){
                try {
                    const correoActual = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/correo");
                    const usuarioPerfilNuevo = {
                        "correo": correoActual.data,
                        "perfilactual": "Empleado"
                    }
                    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/cambiarPerfil/", usuarioPerfilNuevo);
                    console.log(registro);
                    RedireccionarAPaginaMenuEmployee();
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
    };
    

</script>


<style>

</style>
