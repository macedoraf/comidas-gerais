package br.com.rafa_macedo.comidas_gerais.recipedetail.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import br.com.rafa_macedo.comidas_gerais.formatImageSize
import br.com.rafa_macedo.comidas_gerais.model.Ingredient
import br.com.rafa_macedo.comidas_gerais.recipedetail.presentation.RecipeDetailViewModel
import br.com.rafa_macedo.comidas_gerais.recipedetail.presentation.RecipeDetailViewState
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : ComponentActivity() {
    companion object {
        const val RECIPE_ID = "recipe-id"
    }

    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewState by viewModel.viewState.collectAsState()
            Screen(viewState = viewState)
        }
    }

    override fun onStart() {
        super.onStart()

        intent.extras?.getLong(RECIPE_ID)?.run {
            viewModel.requestRecipeInfo(this)
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun Preview() {
//        Screen(viewState = RecipeDetailViewState())
    }

    @Composable
    private fun Screen(viewState: RecipeDetailViewState) {
        BannerContent()

        Column {
            SummaryContent(viewState.recipeDescription)
            IngredientsContent(viewState.recipeIngredients)
            StepByStepContent()
        }


    }

    @Composable
    private fun BannerContent() {
        val contentDescription = ""
        val imageUrl = ""
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl.formatImageSize("636x393"))
                .size(Size.ORIGINAL)
                .build()
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun StepByStepContent() {
        Text(text = "Step by Step")
    }

    @Composable
    private fun IngredientsContent(ingredients: List<Ingredient>) {
        val lazyListState = rememberLazyListState()
        LazyColumn(state = lazyListState) {
            item { Text(text = "Ingredients") }
            items(ingredients) { item ->
                Text(text = item.name)
            }
        }
    }


    @Composable
    private fun SummaryContent(summary: String) {
        Text(text = "Description")
        Text(text = summary)
    }


}