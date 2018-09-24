package com.ongrin.presentation

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the view model input type
 * @param <D> the domain model output type
 */
interface BaseMapper<V, D> {

    fun mapToView(type: D): V

    fun mapFromView(type: V): D

}