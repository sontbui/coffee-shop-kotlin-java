import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieticketbookingkotlin.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSelectLanguage: (String) -> Unit,
    selectedLanguage: String
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var tempSelectedLanguage by remember { mutableStateOf(selectedLanguage) }

    LaunchedEffect(selectedLanguage) {
        println("Current seletec language is: $selectedLanguage")
        tempSelectedLanguage = selectedLanguage
    }

    if (showDialog) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = bottomSheetState,
            containerColor = Color.DarkGray
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.select_language),
                    color = colorResource(R.color.white),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))

                listOf("en" to "English", "vi" to "Vietnamese").forEach { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                               tempSelectedLanguage = code
                            },

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tempSelectedLanguage == code,
                            onClick = {
                                tempSelectedLanguage = code
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.white),
                                unselectedColor = colorResource(id = R.color.white)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            name,
                            modifier = Modifier.weight(1f),
                            color = colorResource(R.color.white)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                            onSelectLanguage(tempSelectedLanguage)
                            onDismiss()
                        }
                              },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFC107)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.select_label),
                        color = colorResource(R.color.white)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
