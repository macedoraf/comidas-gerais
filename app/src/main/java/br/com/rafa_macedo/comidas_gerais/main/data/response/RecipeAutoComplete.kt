package br.com.rafa_macedo.comidas_gerais.main.data.response

sealed class RecipeAutoComplete {
    data class Request(val query: String, val number: Int)
    data class Response(val id: Long, val title: String, val imageType: String)
    data class Item(val id: Long, val title: String, val imageType: String)
    object Mapper {
        fun toItem(response: Response): Item = response.run { Item(id, title, imageType) }
        fun toItems(responses: List<Response>): List<Item> = responses.map { toItem(it) }
    }
}
