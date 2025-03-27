package com.example.movieticketbookingkotlin.ui.screen

import LanguageBottomSheet
import LanguageManager
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieticketbookingkotlin.R
import com.example.movieticketbookingkotlin.ui.screen.slider.WelcomeSlide

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("en") }
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment =  Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.short_app_name),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(Color.DarkGray)
            ) {
                Text(
                    text = stringResource(R.string.language_type),
                    color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.welcome_content),
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))
        WelcomeSlide()
        Spacer(modifier = Modifier.height(30.dp))

//        Text(
//            text = stringResource(R.string.hello),
//            color = Color.Gray,
//            fontSize = 16.sp,
//            textAlign = TextAlign.Center
//        )


        Column(
            modifier= Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = { /* TODO: Xử lý đăng nhập */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(R.color.black),
                    containerColor = colorResource(R.color.button_yellow)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { /* TODO: Xử lý đăng ký */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.policy_welcome),
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }
    }
    if (showDialog) {
        LanguageBottomSheet(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onSelectLanguage = { lang ->
                selectedLanguage = lang
                println("New selected language: $selectedLanguage") // Debug
                (context as? Activity)?.let { activity ->
                    LanguageManager(activity).setLocale(activity, lang)
                }
            },
            selectedLanguage = selectedLanguage
        )
    }

}
//