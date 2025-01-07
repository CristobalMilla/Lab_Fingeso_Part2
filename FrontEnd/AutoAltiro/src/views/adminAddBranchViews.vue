<script setup>

</script>

<template>
    <div>EN CONSTRUCCION</div>
    <div class="alsoButtons">
        <router-link to="/menuAdmin">
          <div class="alsoButton" @click="regresar">Regresar al menu anterior</div>
        </router-link>
    </div>
     <div class="alsoButtons">
        <router-link to="/inicio">
            <div class="alsoButton" @click="logout">Logout</div>
        </router-link>
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
    export default{
        methods:{
            regresar(){
                redireccionarAPaginaAdministrador();
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