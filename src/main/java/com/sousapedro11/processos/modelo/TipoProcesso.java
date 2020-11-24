package com.sousapedro11.processos.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sousapedro11.processos.anotacao.AtribuirToString;

@Entity
@Table(schema = "processos", name = "TipoProcesso")
public class TipoProcesso extends ObjetoBase<TipoProcesso, Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // FIXME Trocar nome gerado
    @AtribuirToString(prefixo = "Id", sufixo = "\n")
    @Column(name = "ID", columnDefinition = "smallint")
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 80)
    @AtribuirToString(prefixo = "Nome", sufixo = "\n")
    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;

    public TipoProcesso() {

    }

    /**
     * @param id
     * @param nome
     */
    public TipoProcesso(final Integer id, @NotBlank @Size(min = 3, max = 80) final String nome) {

        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {

        return this.id;
    }

    public void setId(final Integer id) {

        this.id = id;
    }

    public String getNome() {

        return this.nome;
    }

    public void setNome(final String nome) {

        this.nome = nome;
    }

}
