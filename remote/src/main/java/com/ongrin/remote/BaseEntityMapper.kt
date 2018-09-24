package com.ongrin.remote

interface BaseEntityMapper<M, E> {

    fun mapFromRemote(type: M): E

    fun mapToRemote(type: E): M

}