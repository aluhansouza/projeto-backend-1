package api.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_AUTORIZADO = "https://www.google.com.br";
    String ORIGIN_NAO_AUTORIZADO = "https://www.uol.com.br";
    String ORIGIN_LOCAL = "http://localhost:8080";






}
