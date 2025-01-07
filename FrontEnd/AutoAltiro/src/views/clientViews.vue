<script setup>


</script>


<template>
  <div class="page-container">
    <div class="content-card">
      <img class="client-logo" src="./media/LogoUsachNegro.png" alt="Logo">
      <h1 class="client-title">Panel de Cliente</h1>
      <div class="menu-section">
        <div class="menu-grid">
          <router-link to="/availableCars" class="menu-item" @click="availableCars">
            <div class="menu-content">
              <span class="icon">🔑</span>
              <span>Ver vehículos disponibles</span>
            </div>
          </router-link>
          <router-link to="/rentCar" class="menu-item" @click="rentCar">
            <div class="menu-content">
              <span class="icon">📅</span>
              <span>Arrendar vehículo</span>
            </div>
          </router-link>
          <router-link to="/userSelfEdit" class="menu-item" @click="userSelfEdit">
            <div class="menu-content">
              <span class="icon">👤</span>
              <span>Editar información personal</span>
            </div>
          </router-link>
          <router-link to="/purchaseHistory" class="menu-item" @click="purchaseHistory">
            <div class="menu-content">
              <span class="icon">🕒</span>
              <span>Ver Historial de Compras</span>
            </div>
          </router-link>
        </div>
      </div>
      <div class="action-buttons">
        <router-link to="/tipoUsuario" class="btn" @click="regresar">
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

<style scoped>
.client-logo {
  width: 120px;
  height: auto;
  margin: 0 auto 1rem;
  display: block;
}

.client-title{
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

.icon {
  font-size: 1.5rem;
}

.action-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  font-weight: 500;
  text-decoration: none;
  transition: all 0.2s;
}

.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
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
