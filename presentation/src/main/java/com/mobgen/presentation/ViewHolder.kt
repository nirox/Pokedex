package com.mobgen.presentation


interface ViewHolder<T> {
    fun bind(value: T)
}