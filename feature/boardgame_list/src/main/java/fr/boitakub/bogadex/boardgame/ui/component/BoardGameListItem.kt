package fr.boitakub.bogadex.boardgame.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.boitakub.boardgame_list.R
import fr.boitakub.bogadex.boardgame.model.CollectionItemWithDetails
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BoardGameListItem(
    item: CollectionItemWithDetails,
    onClick: (CollectionItemWithDetails) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = {
                onClick(item)
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(contentAlignment = Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                    .size(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = item.item.coverUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alpha = 0.2f,
                )
                AsyncImage(
                    modifier = Modifier.padding(4.dp),
                    model = item.item.coverUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
            }
            if (item.details != null && item.details?.type == "boardgameexpansion") {
                Icon(
                    modifier = Modifier.padding(4.dp).size(16.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_puzzle_piece_solid),
                    contentDescription = stringResource(id = R.string.expansion_icon),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }
        Column(
            modifier = Modifier.weight(1.0f),
        ) {
            item.item.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item.details?.let {
                    BoardGameInfo(
                        titleRes = R.string.players,
                        iconRes = R.drawable.ic_people_group_duotone,
                        minValue = it.minPlayer,
                        maxValue = it.minPlayer,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.secondary),
                )
                item.details?.recommendedPlayers?.let { values ->
                    if (values.isNotEmpty()) {
                        BoardGameRecommendInfo(
                            iconRes = R.drawable.ic_people_group_duotone,
                            recommendedValue = values[0],
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.secondary),
                )
                BoardGameInfo(
                    titleRes = R.string.players,
                    iconRes = R.drawable.ic_watch_duotone,
                    minValue = item.details?.minPlayTime,
                    maxValue = item.details?.maxPlayTime,
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                        .background(color = MaterialTheme.colorScheme.secondary),
                )
            }
        }
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BoardGameWeightInfo(
                titleRes = R.string.weight,
                iconRes = R.drawable.ic_weight,
                value = item.details?.statistic?.averageWeight,
                tintColor = getWeightColor(averageWeight = item.details?.statistic?.averageWeight),
            )
            item.details?.statistic?.let {
                Surface(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = PolyShape(it.average.toInt(), 85.0f),
                    shadowElevation = 6.dp,
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "%.1f".format(it.average),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                }
            }
        }
    }
}

@Composable
fun getWeightColor(averageWeight: Float?): Color {
    if (averageWeight == null) {
        return MaterialTheme.colorScheme.secondary
    }
    var color = colorResource(android.R.color.white)
    if (averageWeight in 0F..1.2F) {
        color = colorResource(R.color.weight_very_easy)
    }
    if (averageWeight in 1.2F..2.4F) {
        color = colorResource(R.color.weight_easy)
    }
    if (averageWeight in 2.4F..3.0F) {
        color = colorResource(R.color.weight_normal)
    }
    if (averageWeight in 3.0F..3.9F) {
        color = colorResource(R.color.weight_hard)
    }
    if (averageWeight in 3.9F..5F) {
        color = colorResource(R.color.weight_very_hard)
    }
    return color
}

class PolyShape(private val sides: Int, private val radius: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = Path().apply { polygon(sides, radius, size.center) })
    }
}

fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat(),
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat(),
        )
    }
    close()
}
