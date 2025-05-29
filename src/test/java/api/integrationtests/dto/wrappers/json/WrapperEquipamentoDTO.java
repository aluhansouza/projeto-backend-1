package api.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperEquipamentoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private EquipamentoEmbeddedDTO embedded;

    public WrapperEquipamentoDTO() {}

    public EquipamentoEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EquipamentoEmbeddedDTO embedded) {
        this.embedded = embedded;
    }


}
