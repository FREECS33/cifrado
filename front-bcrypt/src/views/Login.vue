<template>
  <b-container fluid class="body">
    <b-row class="card">
      <b-col class="card2">
        <b-form @submit.prevent="submitLogin" v-if="!submitted" class="form">
          <h3 id="heading">Login</h3>
          <b-form-group class="field">
            <b-form-input
              id="email"
              type="email"
              v-model="credentials.email"
              placeholder="Correo electrónico"
              class="input-field"
              required
            >
            </b-form-input>
          </b-form-group>
          <b-form-group class="field">
            <b-form-input
              id="password"
              type="password"
              v-model="credentials.password"
              placeholder="Contraseña"
              class="input-field"
              required
            >
            </b-form-input>
          </b-form-group>
          <b-button type="submit" class="custom-button" block
            >Iniciar sesión</b-button
          >
        </b-form>
        <!-- <div v-if="submited && errorMessage" class="text-danger">{errorMessage}</div>-->
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import forge from "node-forge";
import Swal from "sweetalert2";
import instance from "../config/http-client.gateway";

export default {
  data() {
    return {
      credentials: {
        email: "",
        password: "",
      },
      errorMessage: "",
      submitted: false,
    };
  },
  methods: {
    async submitLogin() {
      const email = this.credentials.email;
      const password = this.credentials.password;
      try {
        const response = await instance.doPost("/auth/", {
          email: email,
          password: password,
        })

        console.log(response.data);

        if (response.status === 200) {
          await Swal.fire({
            title: "¡Bienvenido!",
            text: `Has ingresado como ${response.data.username}`,
            icon: "success",
          });
        }

        // Manejar la respuesta de la API
      } catch (error) {
        await Swal.fire({
          title: "¡Error!",
          text: "Acceso denegado",
          icon: "error",
        });
        // Manejar los errores
      }
    },
  },
};
</script>
<style>
#heading {
  text-align: center;
  margin: 2em;
  color: rgb(255, 255, 255);
  font-size: 1.2em;
}

.input-field {
  margin-top: 10px;
}

.body {
  background-color: #353942;
  padding: 100px;
}

.card {
  margin: auto;
  width: 400px;
  height: 350px;
  background-image: linear-gradient(163deg, #00ff75 0%, #3700ff 100%);
  border-radius: 22px;
  transition: all 0.3s;
}

.card2 {
  width: 400px;
  height: 350px;
  border-radius: 0;
  transition: all 0.2s;
}

.card2:hover {
  transform: scale(0.98);
  border-radius: 20px;
}

.card:hover {
  box-shadow: 0px 0px 30px 1px rgba(0, 255, 117, 0.3);
}

.form {
  display: flex;
  width: 375px;
  height: 345px;
  margin-top: 2px;
  flex-direction: column;
  gap: 10px;
  padding-left: 2em;
  padding-right: 2em;
  padding-bottom: 0.4em;
  background-color: #171717;
  border-radius: 25px;
  transition: 0.4s ease-in-out;
}

.body-login {
  margin: auto;
  border-color: #353942;
}

.body {
  width: 100%;
  height: 100vh;
}

.custom-button {
  margin-top: 10px;
  background-color: #353942;
}
</style>
