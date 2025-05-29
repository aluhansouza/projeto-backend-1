package api.integrationtests.dto.wrappers.xmlandyaml;

import api.integrationtests.dto.EquipamentoDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PagedModelEquipamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "content")
    public List<EquipamentoDTO> content;

    public PagedModelEquipamento() {}

    public List<EquipamentoDTO> getContent() {
        return content;
    }

    public void setContent(List<EquipamentoDTO> content) {
        this.content = content;
    }


}
