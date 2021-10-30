package com.anatame.stemx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.anatame.stemx.ui.theme.AccentColor
import com.anatame.stemx.ui.theme.NeutralBackground
import com.anatame.stemx.ui.theme.StemXTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily =  FontFamily(
            Font(R.font.segoe_ui, FontWeight.Normal),
            Font(R.font.segoe_ui_bold, FontWeight.Bold),
            Font(R.font.segoe_ui_italic, style = FontStyle.Italic),
        )
        setContent {

            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = false

            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = NeutralBackground,
                    darkIcons = useDarkIcons
                )

                // setStatusBarsColor() and setNavigationBarsColor() also exist
            }
            Home(fontFamily = fontFamily)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Home(fontFamily: FontFamily){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = NeutralBackground)
    ) {
        HeadText(
            modifier = Modifier.padding(top = 46.dp, start = 32.dp),
            fontFamily = fontFamily
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        FeaturedSection()
    }
}

@Composable
fun HeadText(modifier: Modifier, fontFamily: FontFamily){
    Text(
        text = buildAnnotatedString {
            append("Welcome to")
            withStyle(
                style = SpanStyle(
                    color = AccentColor
                )
            ){
                append("\nStemX")
            }
        },
        color = Color.White,
        fontSize = 37.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        fontFamily = fontFamily
    )
}
@ExperimentalPagerApi
@Preview
@Composable
fun FeaturedSection(){
    Column(modifier = Modifier.height(180.dp)) {
        HorizontalPager(
            count = 10,
            modifier = Modifier.fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 52.dp)
        ) { page ->
            Column(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                // Card content
                FeaturedCards()
            }
        }
    }
}

@Composable
fun FeaturedCards(){
    Column(modifier = Modifier
        .width(317.dp)) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .height(165.dp)
                .fillMaxWidth()
        ){
            Text(text = "test")
        }
    }
}

