package br.com.rafa_macedo.comidas_gerais.main.presentation


sealed class MainEffect {
    data class Navigate(val clazz: Class<*>, val args: Map<String, Long>? = null) : MainEffect()
}