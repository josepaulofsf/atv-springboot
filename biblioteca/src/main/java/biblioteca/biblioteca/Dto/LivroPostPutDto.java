package biblioteca.biblioteca.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroPostPutDto {

    @JsonProperty
    @NotBlank(message = "O livro deve ter um isbn")
    private Long isbn;

    @JsonProperty("titulo")
    @NotBlank(message = "Titulo do livro não pode ser vazio")
    private String titulo;

    @JsonProperty("ano")
    @NotBlank(message = "Ano do livro não pode ser vazio")
    private int ano;

    @JsonProperty("edicao")
    @NotBlank(message = "O livro tem que ter uma edicao")
    private String edicao;
}