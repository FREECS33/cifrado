import { encrypt, decrypt } from "../utils/security/aes";
import instance from "./axios";

instance.interceptors.request.use(
    //cambiar el objeto del data por la cadena encryptada
    (config) => {
        const data = config.data;
        config.headers = {
            "Content-Type": "application/x-www-form-urlencoded",
        };
        if (data) {
            config.data = encrypt(data);
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    (response) => {
        const data = response.data;
        if (data) {
            response.data = decrypt(data);
        }
        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
);


export default {
    async doPost(url, data) {
        return await instance.post(url, data);
    }
};