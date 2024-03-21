package de.jugda.de.jugda.knanogpt.core.data

interface DataProvider<T> {
    fun load(): T
}