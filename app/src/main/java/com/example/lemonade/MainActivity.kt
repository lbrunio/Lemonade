package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                Lemon()
            }
        }
    }
}

@Composable
fun Lemon(modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center)
) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    var step by remember { mutableStateOf(1) }
    var squeezeDone by remember { mutableStateOf(0) }
    var squeezeNeeded by remember { mutableStateOf((2..4).random()) }

    Column(
        modifier = modifier.clickable {
            when (step) {
                // Cuando step es 1(arbol de limon), le asignamos 2, para avanzar a la siguiente(que es el limon)
                1 -> {
                    step = 2
                }

                // Si estamos en el limon
                2 -> {
                    // Verificamos si la cantidad de exprimidos realizados es menor que la cantidad necesaria
                    if (squeezeDone < squeezeNeeded) {
                        squeezeDone++ // // Si es asi, incrementamos la cantidad de exprimidos realizados

                        // Si la cantidad de exprimidos realizados alcanza la cantidad necesaria, avanzamos al siguiente(que es la limonada)
                        if (squeezeDone == squeezeNeeded) {
                            step = 3

                            // Reiniciamos la cantidad de exprimidos realizados a 0
                            squeezeDone = 0

                            // Generamos una nueva cantidad necesaria de exprimidos aleatoria entre 2 y 4
                            squeezeNeeded = (2..4).random()
                        }
                    }
                }

                // Cuando step es 3(limonada), le asignamos 4, para avanzar a la siguiente(que es el vaso vacio)
                3 -> {
                    step = 4
                }

                // Cuando step es 4(vaso vacio), le asignamos 1, para volver al principio(que es el arbol de limon)
                4 -> {
                    step = 1
                }
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val imageSet = when (step) {
            1 -> R.drawable.lemon_tree
            2 -> R.drawable.lemon_squeeze
            3 -> R.drawable.lemon_drink
            4 -> R.drawable.lemon_restart
            else -> R.drawable.lemon_tree
        }

        val stringSet = when (step) {
            1 -> R.string.tree
            2 -> R.string.squeeze
            3 -> R.string.drink
            4 -> R.string.restart
            else -> R.string.tree
        }

        Image(
            painter = painterResource(id = imageSet),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .border(BorderStroke(6.dp, rainbowColorsBrush), CircleShape)
                .padding(6.dp)
                .clip(CircleShape)
                .background(Color(0xFFF7BFBE))
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(stringResource(id = stringSet))
    }
}

@Preview(showBackground = true)
@Composable
fun LemonApp() {
    LemonadeTheme {
        Lemon()
    }
}
