<script setup>
    import axios from 'axios';
    import {ref, onBeforeMount} from 'vue';
    let developer = false;
    let admin = false;
    const client = ref(false);
    let employee = false;
    const perfiles = ref([]);
    const loading = ref(true);
    onBeforeMount(async ()=> {
        try {
            const idUsuario = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/id");
            const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/obtenerPerfilesById/" + idUsuario.data);
            perfiles.value = respuesta.data;
            console.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            if(perfiles.value.includes('desarrollador')){
                developer = true;
            };
            if(perfiles.value.includes("administrador")){
                admin = true;
            };
            if(perfiles.value.includes('Cliente')){
                console.log("AAAAAAAAAA");
                client.value = true;
                console.log(client);
            };
            if(perfiles.value.includes("empleado")){
                employee = true;
            };
        } catch (error) {
            alert(error);
        } finally {
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
                    <div class="alsoButton" @click="handleChange">Ingresar como desarrollador</div>
                </router-link>
            </div>
            <div class="inputContainers" v-if="admin">
                <router-link to ='/administrador'>
                    <div class="alsoButton" @click="handleChange">Ingresar como administrador</div>
                </router-link>
            </div>
            <div class="inputContainers" v-if="client">
                <router-link to ='/cliente'>
                    <div class="alsoButton" @click="handleChange">Ingresar como cliente</div>
                </router-link>
            </div>
            <div class="inputContainers" v-if="employee">
                <router-link to ='/empleado'>
                    <div class="alsoButton" @click="handleChange">Ingresar como empleado</div>
                </router-link>
            </div>
            <div class="headerDescription" v-else>Su usuario no tiene los permisos correctos</div>
            <div class="inputContainers">
                <div class="alsoButton" @click="recuperarIdUsuario">XD</div>
                <div class="alsoButton" @click="permisos">XDDDDDDDD</div>
            </div>
        </div>
    </div>
   </main>
</template>


<script>
    import axios from 'axios';
    //Redireccionamientos
    //Menu Admin
    function redireccionarAPaginaMenuAdmin(){
        window.location.href = '/menuAdmin';
    
}
    export default{
        methods:{
            async recuperarIdUsuario(){
                try {
                    const idUsuario = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/id");
                    console.log(idUsuario);
                } catch (error) {
                    alert("No se pudo recuperar el Id del usuario logueado");
                } 
            },
            async permisos(){
                //Solcitud de datos backend
                try {
                    const idUsuario = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/id");
                    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/obtenerPerfilesById/" + idUsuario.data);
                    console.log(respuesta);
                    /*
                    if(listaTipos.includes("desarrollador")){
                        developer = true;
                    };
                    if(listaTipos.includes("administrador")){
                        admin = true;
                    };
                    if(listaTipos.includes("cliente")){
                        client = true;
                    };
                    if(listaTipos.includes("empleado")){
                        employer = true;
                    };*/ 

                } catch (error) {
                    alert("No se pudo recuperar la lista de perfiles del usuario logueado");
                }
            },
        }
        
    };
    

</script>


<style>

</style>