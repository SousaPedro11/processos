package com.sousapedro11.processos.anotacao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacaoo criada para filtrar no Hashcode e Equal atributos a serem
 * desconsiderados.
 *
 * @author Pedro Sousa
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnorarHashcodeEquals {

    /**
     * Desativar propriedade Equals
     *
     * @return true or false
     */
    boolean desativarEquals() default true;

    /**
     * Desativar propriedade HashCode
     *
     * @return true or false
     */
    boolean desativarHashCode() default true;
}
