### Proyecto de Encriptación de Datos Frontend y Backend 

Este mini proyecto implementa un sistema de encriptación de datos para garantizar la seguridad de la comunicación entre el frontend y el backend de una aplicación web. Se utilizan algoritmos de cifrado AES (Advanced Encryption Standard) para cifrar y descifrar los datos sensibles, asegurando así su confidencialidad durante la transmisión y almacenamiento.

*Métodos de Cifrado Utilizados*
AES (Advanced Encryption Standard)
AES es un algoritmo de cifrado simétrico ampliamente utilizado que ofrece un alto nivel de seguridad. En este proyecto, se utiliza AES en modo CBC (Cipher Block Chaining) con PKCS5Padding para cifrar y descifrar los datos.

El modo CBC utiliza un vector de inicialización (IV) único para cada mensaje, lo que aumenta la seguridad del cifrado al introducir aleatoriedad en cada cifrado individual. El relleno PKCS5Padding se utiliza para asegurar que los datos a cifrar tengan una longitud compatible con el tamaño del bloque de cifrado.


Frontend
En el frontend, se utiliza JavaScript para cifrar los datos antes de enviarlos al servidor y para descifrar los datos recibidos del servidor.
- Archivo AES.js en utils/security: Este archivo contiene las funciones encrypt y decrypt que implementan el cifrado y descifrado utilizando el algoritmo AES en modo CBC (Cipher Block Chaining). Se utiliza la biblioteca node-forge para manejar la criptografía en el navegador.
- Archivo HTTP-CLIENT.GATEWAY.js en config: Este archivo configura un interceptor para las solicitudes HTTP salientes y entrantes. En la solicitud saliente, se cifran los datos antes de enviarlos al servidor utilizando la función encrypt. En la respuesta entrante, se descifran los datos recibidos utilizando la función decrypt.

Backend
En el backend, se utiliza Java para cifrar y descifrar los datos sensibles antes de almacenarlos en la base de datos y después de recibirlos del cliente.
- Archivo Encrypt.java en utils: Este archivo proporciona métodos para cifrar y descifrar datos utilizando el algoritmo AES en modo CBC. Se utiliza la clase Cipher de Java para manejar la criptografía en el servidor.
- Archivo UserService.java en service: Este servicio interactúa con la base de datos y cifra la contraseña del usuario antes de almacenarla utilizando la función encrypt del archivo Encrypt.java.