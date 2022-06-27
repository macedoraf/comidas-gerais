package br.com.rafa_macedo.comidas_gerais.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafa_macedo.comidas_gerais.R
import br.com.rafa_macedo.comidas_gerais.formatImageSize
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSimpleItem
import br.com.rafa_macedo.comidas_gerais.main.presentation.MainEffect
import br.com.rafa_macedo.comidas_gerais.main.presentation.MainViewModel
import br.com.rafa_macedo.comidas_gerais.main.presentation.MainViewState
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewState by viewModel.viewState.collectAsState()
            val viewEffects = viewModel.viewEffects
            EffectsHandler(viewEffects = viewEffects)
            Screen(
                viewState,
                viewModel::onQueryChanged,
                viewModel::onDoneActionClick,
                viewModel::onClearClick,
                viewModel::onAutoCompleteItemClick
            )
        }
    }

    @Composable
    private fun EffectsHandler(viewEffects: Flow<MainEffect>) {
        LaunchedEffect(key1 = viewEffects) {
            viewEffects.collect { effects ->
                when (effects) {
                    is MainEffect.Navigate -> {
                        startActivity(Intent(this@MainActivity, effects.clazz).apply {
                            effects.args?.forEach {
                                putExtra(it.key, it.value)
                            }
                        })
                    }
                }
            }
        }
    }

    @Composable
    fun Screen(
        viewState: MainViewState,
        onQueryChanged: (String) -> Unit,
        onDoneActionClick: () -> Unit,
        onClearClick: () -> Unit,
        onItemClick: (RecipeAutoComplete.Item) -> Unit
    ) {
        val querySearchState = viewState.querySearchState

        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                AutoCompleteTextView(
                    modifier = Modifier.fillMaxWidth(),
                    query = querySearchState.query,
                    queryLabel = getString(R.string.recipe_search_label),
                    showClearIcon = querySearchState.showClearIcon,
                    onQueryChanged = onQueryChanged,
                    predictions = querySearchState.predictions,
                    onDoneActionClick = onDoneActionClick,
                    onClearClick = onClearClick,
                    onItemClick = onItemClick,
                    itemContent = { Text(text = it.title) }
                )
            }

            Row(Modifier.fillMaxWidth()) {
                RecipeItemList(
                    recipes = viewState.recipeListViewState.items,
                    onRecipeItemClick = viewModel::onRecipeItemClick
                ) { RecipeItem(item = it) }
            }
        }


        // Show list of recipes after search
    }


    @Composable
    fun RecipeItem(item: RecipeSimpleItem, modifier: Modifier = Modifier) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.image.formatImageSize("636x393"))
                .size(Size.ORIGINAL)
                .build()
        )

        Card(
            modifier = modifier.height(200.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp
        ) {


            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painter,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 350f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = item.title,
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
            }
        }
    }

    @Composable
    fun RecipeItemList(
        recipes: List<RecipeSimpleItem>,
        onRecipeItemClick: (RecipeSimpleItem) -> Unit,
        itemContent: @Composable (RecipeSimpleItem) -> Unit = {}
    ) {
        val lazyListState = rememberLazyListState()
        val view = LocalView.current

        LazyColumn(state = lazyListState) {
            items(recipes) { recipe ->
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            view.clearFocus()
                            onRecipeItemClick(recipe)
                        }) { itemContent(recipe) }
            }
        }
    }

    @Composable
    fun <T> AutoCompleteTextView(
        modifier: Modifier,
        query: String,
        queryLabel: String,
        showClearIcon: Boolean,
        onQueryChanged: (String) -> Unit = {},
        predictions: List<T>,
        onDoneActionClick: () -> Unit = {},
        onClearClick: () -> Unit = {},
        onItemClick: (T) -> Unit = {},
        itemContent: @Composable (T) -> Unit = {}
    ) {
        val view = LocalView.current
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            modifier = modifier.heightIn(max = TextFieldDefaults.MinHeight * 6)
        ) {
            item {
                QuerySearch(
                    query = query,
                    label = queryLabel,
                    showClearIcon = showClearIcon,
                    onQueryChanged = onQueryChanged,
                    onDoneActionClick = {
                        view.clearFocus()
                        onDoneActionClick()
                    },
                    onClearClick = onClearClick
                )
            }

            if (predictions.isNotEmpty()) {
                items(predictions) { prediction ->
                    Row(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                view.clearFocus()
                                onItemClick(prediction)
                            }
                    ) {
                        itemContent(prediction)
                    }
                }
            }

        }
    }

    @Composable
    fun QuerySearch(
        modifier: Modifier = Modifier,
        query: String,
        label: String,
        showClearIcon: Boolean,
        onDoneActionClick: () -> Unit = {},
        onClearClick: () -> Unit = {},
        onQueryChanged: (String) -> Unit
    ) {
        var isFocused by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            value = query,
            onValueChange = onQueryChanged,
            label = { Text(text = label) },
            textStyle = MaterialTheme.typography.labelMedium,
            singleLine = true,
            trailingIcon = {
                if (showClearIcon && isFocused) {
                    IconButton(onClick = onClearClick) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Clear")
                    }

                }
            },
            keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            )

        )

    }
}


