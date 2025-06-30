package com.niolasdev.randommouse.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.niolasdev.network.FLAG_API_BASE
import com.niolasdev.randommouse.R
import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.ui.CatDetailState
import com.niolasdev.randommouse.ui.CatDetailViewModel
import com.niolasdev.randommouse.ui.theme.RandomMouseTheme

@Composable
fun CatDetailScreen(
    catId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CatDetailViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CatDetailCard(
        state = state,
        modifier = modifier,
    )
}

@Composable
internal fun CatDetailCard(
    state: CatDetailState,
//    navController: NavController,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onViewMoreDetails: (Cat) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            when (state) {
                is CatDetailState.Loading -> {
                    // TODO Loading
                }

                is CatDetailState.Error -> {
                    // TODO Error
                }

                is CatDetailState.Data -> {
                    val cat = state.cat

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Header with image
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(cat.url)
                                    .placeholder(R.drawable.cat_placeholder_1)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Cat image",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.FillHeight
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = cat.breeds?.firstOrNull()?.name ?: "Cat #${cat.id}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "ID: ${cat.id}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Breed info
                        cat.breeds?.firstOrNull()?.let { breed ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Breed Information",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                BreedInfoRow("Name", breed.name)
                                breed.temperament?.let { temperament ->
                                    BreedInfoRow("Temperament", temperament)
                                }
                                breed.description?.let { description ->
                                    BreedInfoRow("Description", description)
                                }
                                breed.origin?.let { origin ->
                                    BreedInfoRow("Origin", origin)
                                }
                            }
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(breed.countryFlagUrl)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .placeholder(R.drawable.flag_placeholder_1)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Origin country flag",
                                modifier = Modifier
                                    .size(48.dp),
                                contentScale = ContentScale.FillHeight
                            )
                        }

                        // Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onClose,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Close")
                            }

                            Button(
                                onClick = { onViewMoreDetails(cat) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("View More Details")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreedInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(100.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun CatDetailCardPreview() {
    RandomMouseTheme {
        CatDetailCard(
            CatDetailState.Data(
                cat = Cat(
                    id = "1",
                    url = "https://cdn2.thecatapi.com/images/1.jpg",
                    breeds = listOf(
                        Breed(
                            id = "abyss",
                            name = "Abyssinian",
                            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                            description = "The Abyssinian is easy to care for and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
                            origin = "Egypt",
                            countryFlagUrl = "${FLAG_API_BASE}EG.svg",
                        )
                    )
                )
            )
        )
    }
}
