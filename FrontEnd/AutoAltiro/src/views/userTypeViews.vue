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
            if(perfiles.value.includes("Empleado")){
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
  <div class="page-container">
    <div class="content-card">
      <img class="logo" src="./media/LogoUsachNegro.png" alt="Logo">
      <h1 class="title">Seleccione su Perfil de Usuario</h1>

      <div class="roles-grid" v-if="developer || admin || client || employee">
        <router-link
            v-if="developer"
            to="/menuDeveloper"
            class="role-card"
        >
          <span class="icon">👨‍💻</span>
          <span class="role-name">Desarrollador</span>
        </router-link>

        <router-link
            v-if="admin"
            to="/menuAdmin"
            class="role-card"
        >
          <span class="icon">👨‍💼</span>
          <span class="role-name">Administrador</span>
        </router-link>

        <router-link
            v-if="client"
            to="/menuClient"
            class="role-card"
        >
          <span class="icon">🧑‍🦲</span>
          <span class="role-name">Cliente</span>
        </router-link>

        <router-link
            v-if="employee"
            to="/menuEmployee"
            class="role-card"
        >
          <span class="icon">👨‍🔧</span>
          <span class="role-name">Empleado</span>
        </router-link>
      </div>

      <div v-else class="no-roles">
        <p>Su usuario no tiene los permisos correctos</p>
      </div>

      <button @click="logout" class="logout-btn">
        Cerrar Sesión
      </button>
    </div>
  </div>
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
                    const registro2 = await axios.get(import.meta.env.VITE_BASE_URL + "api/usuario/obtenerUsuario/" + usuarioPerfilNuevo.correo + '/');
                    console.log(registro);
                    console.log(registro2);
                    localStorage.setItem("usuarioActual",JSON.stringify(usuarioPerfilNuevo));
                    localStorage.setItem("usuario",JSON.stringify(registro2.data));
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


<style scoped>
.page-container {
  min-height: 100vh;
  padding: 2rem;
  background: #5c6bb5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content-card {
  width: 100%;
  max-width: 800px;
  background: #a9b7fc;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  padding: 2rem;
  text-align: center;
}

.logo {
  width: 120px;
  height: auto;
  margin-bottom: 1.5rem;
}

.title {
  color: #2c3e50;
  font-size: 1.75rem;
  margin-bottom: 2rem;
}

.roles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.role-card {
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  padding: 1.5rem;
  border-radius: 12px;
  color: white;
  text-decoration: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  transition: all 0.2s;
}

.role-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.icon {
  font-size: 2rem;
}

.role-name {
  font-size: 1.1rem;
  font-weight: 500;
}

.no-roles {
  background: #fff5f5;
  color: #e53e3e;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 2rem;
}

.logout-btn {
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #e53e3e, #c53030);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
}

.logout-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .page-container {
    padding: 1rem;
  }

  .content-card {
    border-radius: 0;
    padding: 1rem;
  }

  .roles-grid {
    grid-template-columns: 1fr;
  }
}
</style>