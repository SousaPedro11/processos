package com.sousapedro11.processos.modelo;

import java.io.Serializable;

public interface IEntidade<K extends Serializable> {

    K getChavePrimaria();
}