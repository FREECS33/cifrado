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
import axios from "axios";

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
      const jsonObject = { email, password };
      const jsonString = JSON.stringify(jsonObject);

      const secretKey = "el@ConGr3z041!12";
      if (!secretKey) {
        throw new Error("La clave secreta no existe padre");
      }

      // Crear un objeto cipher utilizando la clave secreta
      const cipher = forge.cipher.createCipher("AES-CBC", secretKey);

      // Generar un IV (Initialization Vector) aleatorio
      const iv = forge.random.getBytesSync(16);

      // Actualizar el objeto cipher con el IV
      cipher.start({ iv });

      // Encriptar la cadena JSON
      cipher.update(forge.util.createBuffer(jsonString, "utf8"));
      cipher.finish();
      const encryptedData = forge.util.encode64(iv + cipher.output.getBytes());
      const encodedDataPost = encodeURIComponent(encryptedData);
      try {
        const response = await axios.post(
          "http://localhost:8080/api/auth/",
          encodedDataPost,
          { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
        );

        const encryptedData2 = response.data;

        // Decodificar la cadena encriptada desde base64
        const decodedEncryptedData = forge.util.decode64(encryptedData2);

        // Extraer el IV y los datos encriptados de la cadena
        const iv2 = decodedEncryptedData.slice(0, 16); // Suponiendo que el IV es de 16 bytes
        const encryptedBytes = decodedEncryptedData.slice(16);

        // Crear un objeto Buffer para los bytes encriptados
        const encryptedBuffer = forge.util.createBuffer(encryptedBytes, "raw");

        // Crear un descifrador
        const decipher = forge.cipher.createDecipher("AES-CBC", secretKey);

        // Establecer el IV
        decipher.start({ iv: iv2 });

        // Actualizar el descifrador con los datos encriptados
        decipher.update(encryptedBuffer);

        // Finalizar la operación de descifrado
        const decryptedData = decipher.finish(); // Devuelve true si la operación fue exitosa
        const decryptedString = decipher.output.toString("utf8");
        // Parsear la cadena desencriptada a JSON
        const decryptedJson = JSON.parse(decryptedString);
        if (decryptedJson) {
          await Swal.fire({
            title: "¡Bienvenido!",
            text: `Has ingresado como ${decryptedJson.username}`,
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
