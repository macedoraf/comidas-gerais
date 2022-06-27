package br.com.rafa_macedo.comidas_gerais.main.presentation

sealed class MainAction {
    object SearchBarClear : MainAction()
    object SearchBarSubmit : MainAction()
}