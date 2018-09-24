package com.ongrin.data

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <E> the remote model input type
 * @param <D> the model return type
 */
interface BaseMapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E
}