package br.com.rafa_macedo.comidas_gerais.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import br.com.rafa_macedo.comidas_gerais.R
import br.com.rafa_macedo.comidas_gerais.presentation.MainViewModel
import br.com.rafa_macedo.comidas_gerais.presentation.MainViewState
import br.com.rafa_macedo.comidas_gerais.ui.theme.ComidasgeraisTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewState by viewModel.viewState.collectAsState()
            Screen(
                viewState.querySearchState,
                viewModel.onQueryChanged,
                viewModel.onDoneActionClick,
                viewModel.onClearClick,
                viewModel.onItemClick
            )
        }

    }

    @Composable
    fun Screen(
        viewState: MainViewState.QuerySearchState, onQueryChanged: (String) -> Unit,
        onDoneActionClick: () -> Unit,
        onClearClick: () -> Unit,
        onItemClick: (String) -> Unit
    ) {

        AutoCompleteTextView(
            modifier = Modifier.fillMaxWidth(),
            query = viewState.query,
            queryLabel = getString(R.string.recipe_search_label),
            onQueryChanged = onQueryChanged,
            predictions = viewState.predictions,
            onDoneActionClick = onDoneActionClick,
            onClearClick = onClearClick,
            onItemClick = onItemClick,
            itemContent = { Text(text = it) }
        )
    }

    @Composable
    fun <T> AutoCompleteTextView(
        modifier: Modifier,
        query: String,
        queryLabel: String,
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
                    onQueryChanged = onQueryChanged,
                    onDoneActionClick = {
                        view.clearFocus()
                        onDoneActionClick()
                    },
                    onClearClick = {
                        onClearClick()
                    }
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
        onDoneActionClick: () -> Unit = {},
        onClearClick: () -> Unit = {},
        onQueryChanged: (String) -> Unit
    ) {
        var showClearButton by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { showClearButton = (it.isFocused) },
            value = query,
            onValueChange = onQueryChanged,
            label = { Text(text = label) },
            textStyle = MaterialTheme.typography.labelMedium,
            singleLine = true,
            trailingIcon = {
                if (showClearButton) {
                    IconButton(onClick = { onClearClick() }) {
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


//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        ComidasgeraisTheme {
//            Content()
//        }
//    }
}


