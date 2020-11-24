package com.sousapedro11.processos.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public class Reflexao {

    public static Object readDeclaredField(final Object instancia, final String nomeAtributo) throws IllegalAccessException {

        return FieldUtils.readDeclaredField(instancia, nomeAtributo, true);
    }

    public static Date toDate(final String date) {

        Date localDate = null;
        final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            localDate = format.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        return localDate;
    }

    public static LocalDateTime toDateTime(final String datetime) {

        LocalDateTime localDateTime = null;
        try {

            localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    public static <T> T getInstance(final Class<T> clazz) throws InstantiationException, IllegalAccessException {

        final var instance = clazz.newInstance();

        return instance;

    }

    /*
     * public static <T> T populaObjeto(final Class<T> clazz) throws InstantiationException, IllegalAccessException {
     * final var instance = clazz.newInstance();
     * final var campos = Stream.of(clazz.getDeclaredFields())
     * .filter(f -> !f.isAnnotationPresent(Id.class))
     * .collect(Collectors.toList());
     * for (final Field field : campos) {
     * field.setAccessible(true);
     * final var valor = Reflexao.getRandomValueToField(field);
     * field.set(instance, valor);
     * }
     * return instance;
     * }
     */

    /*
     * private static Object getRandomValueToField(final Field field) throws InstantiationException, IllegalAccessException {
     * Object campo = null;
     * final Class<?> tipo = field.getType();
     * if (tipo.isEnum()) {
     * final Object[] enumValues = tipo.getEnumConstants();
     * campo = enumValues[RandomUtils.nextInt(0, enumValues.length)];
     * } else if (Reflexao.isNumber(tipo)) {
     * campo = Reflexao.toNumber(tipo);
     * } else if (String.class.isAssignableFrom(tipo)) {
     * Object gerarString = null;
     * if (!field.isAnnotationPresent(CEP.class)) {
     * final var lenght = field.getAnnotation(Column.class).length();
     * final var lettras = true;
     * final var numeros = false;
     * gerarString = RandomStringUtils.random(lenght, lettras, numeros);
     * } else {
     * final var sb = new StringBuilder();
     * sb.append(RandomUtils.nextInt(10000, 99999 + 1));
     * sb.append("-");
     * sb.append(RandomUtils.nextInt(100, 999 + 1));
     * gerarString = sb.toString();
     * }
     * campo = gerarString;
     * } else if (tipo.isAssignableFrom(Boolean.TYPE)) {
     * campo = RandomUtils.nextBoolean();
     * } else if (Date.class.isAssignableFrom(tipo)) {
     * try {
     * final var rand = RandomUtils.nextLong() % System.currentTimeMillis();
     * final var resultdate = new Date(rand);
     * campo = resultdate;
     * } catch (final Exception e) {
     * e.printStackTrace();
     * }
     * } else if (LocalDateTime.class.isAssignableFrom(tipo)) {
     * final var resultdatetime = LocalDateTime.now();
     * campo = resultdatetime;
     * }
     * return campo;
     * }
     */

    public static boolean isNumber(final Class<?> tipo) {

        return Stream.of(byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class, Long.class, BigInteger.class,
                        float.class, Float.class, double.class, Double.class, BigDecimal.class)
                        .filter(c -> c.isAssignableFrom(tipo))
                        .findAny()
                        .isPresent();
    }

    private static Object toNumber(final Class<?> tipo) {

        Object campo = null;

        if (Byte.class.isAssignableFrom(tipo)) {
            campo = RandomUtils.nextBytes(1);
        } else if (Short.class.isAssignableFrom(tipo) || short.class.isAssignableFrom(tipo)) {
            campo = (short) RandomUtils.nextInt(0, Short.MAX_VALUE + 1);
        } else if (Integer.class.isAssignableFrom(tipo) || int.class.isAssignableFrom(tipo)) {
            campo = RandomUtils.nextInt(0, 9999 + 1);
        } else if (Long.class.isAssignableFrom(tipo) || long.class.isAssignableFrom(tipo)) {
            campo = RandomUtils.nextLong();
        } else if (BigInteger.class.isAssignableFrom(tipo)) {
            campo = BigInteger.valueOf(RandomUtils.nextInt());
        } else if (Float.class.isAssignableFrom(tipo) || float.class.isAssignableFrom(tipo)) {
            campo = RandomUtils.nextFloat();
        } else if (Double.class.isAssignableFrom(tipo) || double.class.isAssignableFrom(tipo)) {
            campo = RandomUtils.nextDouble();
        } else if (BigDecimal.class.isAssignableFrom(tipo)) {
            campo = BigDecimal.valueOf(RandomUtils.nextDouble());
        }

        return campo;
    }

    /*
     * public static <T> void modificar(final T entidade) throws InstantiationException, IllegalAccessException {
     * Stream.of(entidade.getClass().getDeclaredFields())
     * .filter(f -> !f.isAnnotationPresent(Id.class))
     * .forEach(f -> {
     * try {
     * f.setAccessible(true);
     * final var valorOld = f.get(entidade);
     * var valorNew = Reflexao.getRandomValueToField(f);
     * if (valorOld == null) {
     * valorNew = Reflexao.getRandomValueToField(f);
     * } else {
     * while (valorOld.equals(valorNew)) {
     * valorNew = Reflexao.getRandomValueToField(f);
     * }
     * }
     * f.set(entidade, valorNew);
     * } catch (final Exception e) {
     * e.printStackTrace();
     * }
     * });
     * }
     */
}
