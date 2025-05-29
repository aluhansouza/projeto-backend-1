package api.integrationtests.dto.wrappers.json;

import api.integrationtests.dto.EquipamentoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class EquipamentoEmbeddedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("equipamentos")
    private List<EquipamentoDTO> equipamentos;

    public EquipamentoEmbeddedDTO() {
    }

    public List<EquipamentoDTO> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<EquipamentoDTO> equipamentos) {
        this.equipamentos = equipamentos;
    }


}
