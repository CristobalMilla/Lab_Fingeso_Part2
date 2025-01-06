<script setup>

</script>


<template>


</template>

<script>
    import axios from 'axios';
    //Volver a pagina anterior
    function redireccionarAPaginaAdministrador(){
        window.location.href = '/menuAdmin';
    }
    //Lougout
    function redireccionarAPaginaPrincipal(){
        window.location.href = '/inicio';
    }
    export default {
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