package api.controller.docs;

import api.dto.EquipamentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface EquipamentoControllerDocs {

@Operation(summary = "Método Listar",
        description = "Método Listar",
        tags = {"Equipamentos"},
        responses = {
                @ApiResponse(
                        description = "Success",
                        responseCode = "200",
                        content = {
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        array = @ArraySchema(schema = @Schema(implementation = EquipamentoDTO.class))
                                )
                        }),
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
)
ResponseEntity<PagedModel<EntityModel<EquipamentoDTO>>> listar(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
);


    @Operation(summary = "Método Buscar por Nome",
            description = "Método Buscar por Nome",
            tags = {"Equipamento"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = EquipamentoDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PagedModel<EntityModel<EquipamentoDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );



@Operation(summary = "Método Buscar por Id",
        description = "Método Buscar por Id",
        tags = {"Equipamentos"},
        responses = {
                @ApiResponse(
                        description = "Success",
                        responseCode = "200",
                        content = @Content(schema = @Schema(implementation = EquipamentoDTO.class))
                ),
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
)
EquipamentoDTO buscarPorId(@PathVariable("id") Long id);

@Operation(summary = "Método Cadastrar",
        description = "\"Método Cadastrar",
        tags = {"Equipamentos"},
        responses = {
                @ApiResponse(
                        description = "Success",
                        responseCode = "200",
                        content = @Content(schema = @Schema(implementation = EquipamentoDTO.class))
                ),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
)
EquipamentoDTO cadastrar(@RequestBody EquipamentoDTO equipamentoDTO);

@Operation(summary = "Método Atualizar",
        description = "Método Atualizar",
        tags = {"Equipamentos"},
        responses = {
                @ApiResponse(
                        description = "Success",
                        responseCode = "200",
                        content = @Content(schema = @Schema(implementation = EquipamentoDTO.class))
                ),
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
)
EquipamentoDTO atualizar(@RequestBody EquipamentoDTO equipamentoDTO);

@Operation(summary = "Método Excluir",
        description = "Método Excluir",
        tags = {"Equipamentos"},
        responses = {
                @ApiResponse(
                        description = "No Content",
                        responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
)
ResponseEntity<?> excluir(@PathVariable("id") Long id);



}