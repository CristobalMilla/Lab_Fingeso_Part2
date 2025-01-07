<script setup>


</script>


<template>
  <div class="page-container">
    <div class="content-card">
      <img class="admin-logo" src="./media/LogoUsachNegro.png" alt="Logo">
      <h1 class="admin-title">Panel de Administración</h1>
      <div class="menu-section">
        <h2 class="section-title">Gestión de Vehículos</h2>
        <div class="menu-grid">
          <router-link to="/addCar" class="menu-item" @click="addCar">
            <div class="menu-content">
              <span class="icon">🚗</span>
              <span>Agregar vehículo</span>
            </div>
          </router-link>
          <router-link to="/adminEditCar" class="menu-item" @click="editCar">
            <div class="menu-content">
              <span class="icon">🔧</span>
              <span>Modificar vehículo</span>
            </div>
          </router-link>
          <router-link to="/eraseCar" class="menu-item" @click="removeCar">
            <div class="menu-content">
              <span class="icon">🗑️</span>
              <span>Eliminar vehículo</span>
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
        window.location.href = '/adminEditCar';
    }
    //Remove Car
    function redireccionarAPaginaRemoveCar(){
        window.location.href = '/adminRemoveUser';
    }
    //Cambiar disponibilidad
    function redireccionarAModificarDisponibilidad(){
      window.location.href = '/editAvailability';
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
            recepcion(){
                redireccionarAModificarDisponibilidad();
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

<style scoped>
.admin-logo {
  width: 120px;
  height: auto;
  margin: 0 auto 1rem;
  display: block;
}

.admin-title {
  text-align: center;
  color: #2c3e50;
  font-size: 2rem;
  margin-bottom: 2rem;
}

.menu-section {
  margin-bottom: 2rem;
}

.section-title {
  color: #2c3e50;
  font-size: 1.25rem;
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid #e0e0e0;
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