<script setup>

</script>

<template>
  <div class="page-container">
    <div class="content-card">
      <img class="logo" src="./media/LogoUsachNegro.png" alt="Logo">

      <h1 class="title">{{ !register ? 'Inicio de Sesión' : 'Registro' }}</h1>
      <p class="subtitle">
        {{ !register ? 'Complete los campos para iniciar sesión' : 'Complete los campos para registrarse' }}
      </p>

      <!-- Login Form -->
      <div v-if="!register" class="form-container">
        <div class="input-group">
          <input
              type="email"
              v-model="username"
              required
              pattern=".+@.+\..+"
              placeholder="Ingrese correo"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="password"
              v-model="password"
              placeholder="Ingrese contraseña"
              class="input-field"
          >
        </div>
        <button class="btn-primary" @click="login">Iniciar Sesión</button>
      </div>

      <!-- Register Form -->
      <div v-else class="form-container">
        <div class="input-group">
          <input
              type="email"
              v-model="usernameRegister"
              required
              pattern=".+@.+\..+"
              placeholder="Ingrese correo"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="password"
              v-model="passwordRegister"
              required
              placeholder="Ingrese contraseña"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="password"
              v-model="passwordRegisterConfirmation"
              required
              placeholder="Confirme contraseña"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="number"
              v-model="rutRegister"
              required
              placeholder="RUT: 123456789"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="text"
              v-model="nameRegister"
              required
              placeholder="Ingrese su nombre"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="number"
              v-model="ageRegister"
              required
              min="18"
              placeholder="Ingrese su edad"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="text"
              v-model="documentNameRegister"
              required
              maxlength="100"
              placeholder="Número de documento"
              class="input-field"
          >
        </div>
        <div class="input-group">
          <input
              type="text"
              v-model="licenceNumberRegister"
              required
              placeholder="Número de licencia"
              class="input-field"
          >
        </div>
        <button class="btn-primary" @click="addUser">Registrarse</button>
      </div>

      <div class="action-buttons">
        <button class="btn-secondary" @click="handleChange">
          {{ !register ? 'Registrarse' : 'Iniciar Sesión' }}
        </button>
        <router-link to="/menuAnon" class="btn-secondary">
          Ingresar sin iniciar sesión
        </router-link>
      </div>
    </div>
  </div>
</template>



<script>

import axios from 'axios'

//Redireccionamiento
//Usuario
function redireccionarAPaginaSeleccionTipoUsuario(){
    window.location.href = '/tipoUsuario';

}
//Usuario Anonimo
function redireccionarAPaginaUsuarioAnonimo(){
    window.location.href = '/menuAnon';
    
}

export default{
    data (){
        return{
            username: '',
            password: '',
            loggedIn: false,
            register: false,
            usernameRegister: '',
            passwordRegister: '',
            passwordRegisterConfirmation: '',
            rutRegister: '',
            nameRegister:'',
            ageRegister:'',
            documentNameRegister:'',
            licenceNumberRegister:'',

        }
    },
    methods:{
        async login(){
            //DEBUGGEO
            //redireccionarASubpaginaSeleccionTipoUsuario();
            //DEBUGGEO

            //Envio de datos a backend
            const usuario = {
                "correo": this.username,
                "contrasena": this.password,
                
            };
            try {
                const respuesta = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/login/", usuario);
                if (respuesta.data == 1){
                    localStorage.setItem('login', JSON.stringify(this.username));
                    redireccionarAPaginaSeleccionTipoUsuario();
                }
                if(respuesta.data == 0){
                    alert("Credenciales Invalidas");
                }
                respuesta.data = 0;
                console.log(respuesta.data);

            } catch (error) {
                alert("No se genera conexion con el servidor")
            }
        },
        handleChange(){
            this.register = !this.register
            console.log(this.register)
        },
        async anonimo(){
            try {
                const registro = await axios.put(import.meta.env.VITE_BASE_URL + "api/usuario/loginAnonimo");
                console.log(registro);
                redireccionarAPaginaUsuarioAnonimo();
            } catch (error) {
                alert(error);
            }
                
        },
        async addUser(){
            //Envio de datos a BackEnd
            if(this.passwordRegister == this.passwordRegisterConfirmation){
                if(this.usernameRegister != "" & this.passwordRegister != "" & this.rutRegister != "" & this.nameRegister != "" & this.ageRegister != "" & this.documentNameRegister != "" & this.licenceNumberRegister != ""){
                    const nuevo_usuario = {
                        "correo": this.usernameRegister,
                        "contrasena": this.passwordRegister,
                        "rut": this.rutRegister,
                        "nombre": this.nameRegister,
                        "edad": this.ageRegister,
                        "carnet": this.documentNameRegister,
                        "licenciaconducir": this.licenceNumberRegister,
                    }
                    try {
                        const registro = await axios.post(import.meta.env.VITE_BASE_URL + "api/usuario/register", nuevo_usuario);
                        console.log(registro)
                        alert("Usuario registrado con exito")
                    } catch (error) {
                        alert("No se pudo registrar")
                    }
                }
                else{
                    alert("Faltan datos para ingresar")
                }
            }
            else{
                alert("Las contraseñas deben coincidir")
            }
        }
    }
}
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
  max-width: 480px;
  background: #a9b7fc;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  padding: 2rem;
}

.logo {
  width: 120px;
  height: auto;
  margin: 0 auto 2rem;
  display: block;
}

.title {
  color: #2c3e50;
  font-size: 2rem;
  margin-bottom: 0.5rem;
  text-align: center;
}

.subtitle {
  color: #4a5568;
  text-align: center;
  margin-bottom: 2rem;
}

.form-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
}

.input-group {
  width: 100%;
}
.input-field {
  width: 100%;
  padding: 0.75rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s;
}

.input-field:focus {
  border-color: #2447bd;
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.1);
  outline: none;
}

.input-field:required:valid {
  border-color: #48bb78;
}

.input-field:required:invalid {
  border-color: #f56565;
}

.btn-primary, .btn-secondary {
  width: 100%;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
  text-align: center;
  text-decoration: none;
}

.btn-primary {
  background: linear-gradient(135deg, #2447bd, #29c9ce);
  color: white;
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.1);
  color: #2c3e50;
  border: 1px solid #e2e8f0;
}

.btn-primary:hover, .btn-secondary:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

@media (max-width: 768px) {
  .page-container {
    padding: 1rem;
  }

  .content-card {
    border-radius: 0;
    padding: 1rem;
  }
}
</style>


