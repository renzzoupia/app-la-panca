package com.braxly.lapancaproject.conexionApi;


import static android.util.Base64.encodeToString;

import android.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public class ConexionApi {
    public static final String URL_BASE = "https://panca.informaticapp.com/";
    public static String AUTH = "Basic YXNkYXdkYXNkYXdkYTpkd2Fkc2F3YWRzZGF3ZA==";
    public static String AUTH_LOGIN = desencriptarAuth();
    public static String userSecreto;
    public static String llaveSecreta;
    public static String userSecretoApi = "a2aa07adfhdfrexfhgdfhdferttgeCSmdvXPaKwjXSA4vaylKggX24LsgKzu";
    public static String llaveSecretaApi = "a2aa07adfhdfrexfhgdfhdferttgeLE63SwOSvliR3mtMX3cDw5Y.r6mtkna";

    public static String desencriptarAuth() {

       // String creds = String.format("%s:%s", userSecretoApi, llaveSecretaApi);
        String combinedString = userSecretoApi + ":" + llaveSecretaApi;
        byte[] data = combinedString.getBytes(StandardCharsets.UTF_8);
        String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);
        //String authConEspacio = Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
        //String AuthSinEspacios = authConEspacio.replaceAll("\\s+", "");
        return "Basic " + base64Encoded;
    }

    public static void compararPass(){
        String passwordFromUser = "12345";  // Reemplaza esto con la contraseña ingresada por el usuario
        String hashedPasswordFromDB = "2a07dfhdfrexfhgdfhdferttgsad";  // Reemplaza esto con el hash almacenado en tu base de datos

        // Verifica si la contraseña ingresada por el usuario coincide con el hash almacenado
        if (BCrypt.checkpw(passwordFromUser, hashedPasswordFromDB)) {
            // La contraseña es correcta, permite el acceso
            // ... Haz lo que sea necesario aquí, por ejemplo, inicia sesión o permite el acceso a la aplicación.
        } else {
            // La contraseña es incorrecta, no permite el acceso
            // ... Maneja el caso de contraseña incorrecta, por ejemplo, muestra un mensaje de error.
        }
    }

}
