package com.sousapedro11.processos.modelo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.stream.Stream;

import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.sousapedro11.processos.anotacao.AtribuirToString;
import com.sousapedro11.processos.anotacao.IgnorarHashcodeEquals;
import com.sousapedro11.processos.util.Reflexao;

public class ObjetoBase<T, K extends Serializable> implements IEntidade<K> {

    @Override
    public int hashCode() {

        final var builder = new HashCodeBuilder();

        Stream.of(this.getClass().getDeclaredFields())
                        .filter(f -> !f.isAnnotationPresent(IgnorarHashcodeEquals.class)
                                        || (f.isAnnotationPresent(IgnorarHashcodeEquals.class)
                                                        && !f.getAnnotation(IgnorarHashcodeEquals.class).desativarHashCode()))
                        .map(Field::getName).forEach(nome -> {

                            try {
                                final var valor = FieldUtils.readDeclaredField(this, nome, true);
                                builder.append(valor);

                            } catch (final IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });

        return builder.toHashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final var other = (T) obj;
        final var builder = new EqualsBuilder();

        Stream.of(this.getClass().getDeclaredFields())
                        .filter(f -> !f.isAnnotationPresent(IgnorarHashcodeEquals.class)
                                        || (f.isAnnotationPresent(IgnorarHashcodeEquals.class)
                                                        && !f.getAnnotation(IgnorarHashcodeEquals.class).desativarEquals()))
                        .map(Field::getName).forEach(nome -> {

                            try {
                                final Object valor1 = Reflexao.readDeclaredField(this, nome);
                                final Object valor2 = Reflexao.readDeclaredField(other, nome);

                                builder.append(valor1, valor2);

                            } catch (final IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });

        return builder.isEquals();
    }

    @Override
    public String toString() {

        final var builder = new StringBuilder();

        FieldUtils.getFieldsListWithAnnotation(this.getClass(), AtribuirToString.class).forEach(f -> {

            try {
                final var valor = FieldUtils.readDeclaredField(this, f.getName(), true);

                final var annotation = f.getAnnotation(AtribuirToString.class);
                builder.append(annotation.prefixo()).append(valor).append(annotation.sufixo());

            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return builder.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public K getChavePrimaria() {

        K chavePrimaria = null;
        final var campos = FieldUtils.getFieldsListWithAnnotation(this.getClass(), Id.class);

        if (!campos.isEmpty()) {

            try {
                chavePrimaria = (K) FieldUtils.readDeclaredField(this, campos.get(0).getName(), true);
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return chavePrimaria;
    }
}
