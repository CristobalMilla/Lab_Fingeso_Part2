<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

// Datos reactivos
const userType = ref('Anonimo'); // Tipo de usuario
const router = useRouter();

// Función para volver a la página de inicio y restablecer el estado del usuario
const volver = async() => {
  try {
    const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/logout/");
    console.log(registro);
    userType.value = ''; // Restablecer el estado del usuario
    console.log("Estado del usuario después de volver:", userType.value); // Log del estado actual
    router.push('/'); // Redirigir a la página de inicio
  } catch (error) {
    alert(error);
  }
};

// Función para ir a la página de vehículos disponibles
const irAVehiculosDisponibles = () => {
  console.log("Estado del usuario actual:", userType.value); // Log del estado actual
  router.push('/availableCars'); // Redirigir a la página de vehículos disponibles
};
</script>

<template>
  <div>
    <h1>Menú Anónimo</h1>
    <div>
      <button @click="volver">Volver</button>
    </div>
    <div>
      <button @click="irAVehiculosDisponibles">Vehículos Disponibles</button>
    </div>
  </div>
</template>
