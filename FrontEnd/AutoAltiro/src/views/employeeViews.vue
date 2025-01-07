<script setup>
// Script inicial para determinar los tipos de usuario (permisos) que el usuario logueado tendría
import axios from 'axios';
import { ref, onBeforeMount } from 'vue';

// Variables iniciales
const developer = ref(false);
const admin = ref(false);
const client = ref(false);
const employee = ref(false);
const perfiles = ref([]);
const loading = ref(true);

// Realizar antes de cargar la página
onBeforeMount(async () => {
  try {
    const idUsuario = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/usuarioActual/id");
    const respuesta = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/obtenerPerfilesById/" + idUsuario.data);
    perfiles.value = respuesta.data;
    console.log("Recuperación de datos correcta");
    if (perfiles.value.includes('Desarrollador')) {
      developer.value = true;
    }
    if (perfiles.value.includes("Administrador")) {
      admin.value = true;
    }
    if (perfiles.value.includes('Cliente')) {
      client.value = true;
    }
    if (perfiles.value.includes("Empleado")) {
      employee.value = true;
    }
  } catch (error) {
    alert(error);
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <div class="page-container" v-if="!loading">
    <div class="content-card">
      <img class="employee-logo" src="./media/LogoUsachNegro.png" alt="Logo">
      <h1 class="employee-title">Panel de Empleado</h1>
      <div class="menu-section">
        <div class="menu-grid">
          <router-link to="/editDelivery" class="menu-item" @click="entrega">
            <div class="menu-content">
              <span class="icon">🚦</span>
              <span>Modificar entrega</span>
            </div>
          </router-link>
          <router-link to="/recepcion" class="menu-item" @click="recepcion">
            <div class="menu-content">
              <span class="icon">🔄</span>
              <span>Cambiar disponibilidad</span>
            </div>
          </router-link>
        </div>
      </div>
      <div class="action-buttons">
        <router-link to="/tipoUsuario" class="btn" @click="returnClient">
          Cambiar Perfil
        </router-link>
        <router-link to="/inicio" class="btn" @click="logout">
          Cerrar Sesión
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
//Volver a pagina anterior
function redireccionarAPaginaSeleccionTipoUsuario(){
  window.location.href = '/tipoUsuario';
}
//Logout
function redireccionarAPaginaPrincipal(){
  window.location.href = '/inicio';
}
export default {
  methods:{
    async returnClient(){
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
    },
    recepcion(){
      redireccionarAModificarDisponibilidad();
    },
    entrega(){
      redireccionarAModificarEntrega();
    }
  }
}
</script>

<style scoped>

.employee-logo {
  width: 120px;
  height: auto;
  margin: 0 auto 1rem;
  display: block;
}

.employee-title{
  text-align: center;
  color: #2c3e50;
  font-size: 2rem;
  margin-bottom: 2rem;
}
.menu-section {
  margin-bottom: 2rem;
}
.menu-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
}

.menu-item {
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
  padding: 1rem;
  border-radius: 8px;
  text-decoration: none;
  border: none;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.menu-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.menu-content {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}
.action-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}
@media (max-width: 768px) {

  .menu-grid {
    grid-template-columns: 1fr;
  }

  .action-buttons {
    flex-direction: column;
  }

  .btn {
    width: 100%;
    text-align: center;
  }
}
</style>