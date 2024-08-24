import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

sealed interface Tile {

    data object Empty: Tile {
        @Composable
        override fun render(modifier: Modifier) = Unit
    }

    data object Grass: Tile {
        @Composable
        override fun render(modifier: Modifier) = Grass(modifier)
    }

    data object Water: Tile {
        @Composable
        override fun render(modifier: Modifier) = Water(modifier)
    }

    data object Apple: Tile {
        @Composable
        override fun render(modifier: Modifier) = Apple(modifier)
    }

    data object Male: Tile {
        @Composable
        override fun render(modifier: Modifier) = Male(modifier)
    }

    @Composable
    fun render(modifier: Modifier)
}

@Composable
private fun Grass(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource("grass.png"),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier,
    )
}

@Composable
private fun Water(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource("water.png"),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
private fun Apple(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource("apple.png"),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
private fun Male(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource("human.png"),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = modifier,
    )
}