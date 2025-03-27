package com.example.movieticketbookingkotlin.ui.screen.slider

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieticketbookingkotlin.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun WelcomeSlide() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val images = listOf(
        R.drawable.poster1,
        R.drawable.poster2,
        R.drawable.poster3
    )

    val descriptions = listOf(
        stringResource(R.string.description_poster3),
        stringResource(R.string.description_poster2),
        stringResource(R.string.description_poster1),
    )


    LaunchedEffect(Unit) {
        while (true) {
            delay(Duration.ofMillis(4500))
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        ) { page ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = descriptions[page],
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}
