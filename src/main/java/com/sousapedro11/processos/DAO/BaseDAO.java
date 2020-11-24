package com.sousapedro11.processos.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sousapedro11.processos.util.SingletonProperties;

public class BaseDAO<T, K extends Serializable> {

    protected static EntityManager conexao = null;

    final EntityManager em = this.getEntityManager();

    public EntityManager getEntityManager() {

        if (BaseDAO.conexao == null) {

            final var persistenceUnitName = SingletonProperties.getInstancia("configuracao.properties").getPropriedade("persistenceUnitName");
            BaseDAO.conexao = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
        }

        return BaseDAO.conexao;
    }

    public final List<T> buscarTodos() {

        final var sql = "Select x From " + this.getClasseEntidade().getSimpleName() + " x";

        return this.getEntityManager().createQuery(sql, this.getClasseEntidade()).getResultList();
    }

    @SuppressWarnings({ "unchecked" })
    public final List<Object[]> buscarComSQL(final String sql) {

        final var query = (TypedQuery<Object[]>) this.em.createNativeQuery(sql);

        // final Query query = this.em.createNativeQuery(sql);

        final var resultList = query.getResultList();

        for (final Object[] object : resultList) {
            for (final Object element : object) {
                System.out.println(element);
            }
        }

        return resultList;
    }

    public Set<T> buscaHibernate(final String hql) {

        @SuppressWarnings("unchecked")
        final Query<T[]> query = ((Session) this.em.getDelegate()).createQuery(hql);

        final Set<T> setList = new TreeSet<>();

        final var list = query.list();

        for (final T[] object : list) {
            for (final T element : object) {
                setList.add(element);
            }
        }

        return setList;
    }

    public List<T> buscaHibernateList(final String hql) {

        @SuppressWarnings("unchecked")
        final Query<T[]> query = ((Session) this.em.getDelegate()).createQuery(hql);

        final List<T> lista = new ArrayList<>();

        final var list = query.list();

        for (final T[] object : list) {
            for (final T element : object) {
                if (!lista.contains(element)) {

                    lista.add(element);
                }
            }
        }

        // return lista.stream().sorted().collect(Collectors.toList());
        return lista;
        // return new TreeSet<>(lista);
    }

    public final Optional<T> buscarPor(final K chavePrimaria) {

        T retorno = null;

        try {

            retorno = this.getEntityManager().find(this.getClasseEntidade(), chavePrimaria.toString().toLowerCase());

        } catch (final Exception e) {

            e.printStackTrace();
        }

        return Optional.ofNullable(retorno);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getClasseEntidade() {

        final var posicaoGenerics = 0;

        return (Class<T>) ((ParameterizedType) this.getClass()
                        .getGenericSuperclass()).getActualTypeArguments()[posicaoGenerics];
    }

    @SuppressWarnings("unchecked")
    protected Class<K> getClasseChavePrimaria() {

        final var posicaoGenerics = 1;

        return (Class<K>) ((ParameterizedType) this.getClass()
                        .getGenericSuperclass()).getActualTypeArguments()[posicaoGenerics];
    }
}
