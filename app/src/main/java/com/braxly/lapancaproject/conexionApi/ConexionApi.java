package com.braxly.lapancaproject.conexionApi;


import static android.util.Base64.encodeToString;

import android.util.Base64;

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

}
