package com.mobgen.presentation.adapter

import android.widget.SearchView

abstract class OnQueryTextListenerAdapter : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean = true
}