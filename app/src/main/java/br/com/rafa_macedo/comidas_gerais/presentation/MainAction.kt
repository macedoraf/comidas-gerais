package br.com.rafa_macedo.comidas_gerais.presentation

sealed class MainAction {
    data class SearchBarUpdate(val query: String)
}