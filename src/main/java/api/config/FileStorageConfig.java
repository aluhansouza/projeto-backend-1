package api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.file")
public class FileStorageConfig {

    /**
     * Diretório base para uploads (ex: /projeto/uploads)
     * Pode ser sobrescrito por variável de ambiente: UPLOAD_DIR
     */
    private String uploadDir = "/projeto/uploads";

    // Getter obrigatório para @ConfigurationProperties
    public String getUploadDir() {
        return uploadDir;
    }

    // Setter obrigatório para @ConfigurationProperties
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    /**
     * Retorna o caminho completo para uploads de materiais
     * Ex: /projeto/uploads/materiais/
     */
    public String getUploadDirMateriais() {
        return uploadDir + "/materiais/";
    }


    /*
    //@Value("${file.upload-dir}")
    //private String uploadDir;

    private String uploadDir = "/projeto/uploads"; // fallback

    @Value("/projeto/uploads/materiais/")
    private String uploadDirMateriais;

    public FileStorageConfig() {}

    public String getUploadDir() {
        return uploadDirMateriais;
    }

    public String getUploadDirMateriais() {
        return uploadDirMateriais;
    }

    public void setUploadDir(String uploadDirMateriais) {
        this.uploadDirMateriais = uploadDirMateriais;
    }


     */
}
