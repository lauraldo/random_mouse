package com.niolasdev.randommouse.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.niolasdev.randommouse.R
import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.ui.theme.RandomMouseTheme

@Composable
fun CatItem(
    cat: Cat,
    modifier: Modifier = Modifier,
    onClick: (Cat) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick(cat)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cat picture
            CatImage(
                imageUrl = cat.url,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Cat description
            CatDescription(
                cat = cat,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CatImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .placeholder(R.drawable.cat_placeholder_1)
                .crossfade(true)
                .build(),
            contentDescription = "Cat image",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillHeight,
            onLoading = { isLoading = true },
            onSuccess = {
                isLoading = false
                isError = false
            },
            onError = {
                isLoading = false
                isError = true
            }
        )

        if (isError) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🐱",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
private fun CatDescription(
    cat: Cat,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Breed name or cat id
        Text(
            text = cat.breeds?.firstOrNull()?.name ?: "Cat #${cat.id}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Temperament or standard description placeholder
        Text(
            text = cat.breeds?.firstOrNull()?.temperament?.let { temperament ->
                if (temperament.length > 100) {
                    temperament.take(100) + "..."
                } else {
                    temperament
                }
            } ?: "A lovely cat with a unique personality",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Cat ID if available, otherwise nothing
        if (cat.breeds?.isNotEmpty() == true) {
            Text(
                text = "ID: ${cat.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview
@Composable
fun CatItemPreview() {
    RandomMouseTheme {
        CatItem(
            Cat(
                id = "my_cat",
                url = "",
                breeds = listOf()
            )
        )
    }
}

@Preview
@Composable
fun CatItemPreviewWithBreed() {
    RandomMouseTheme {
        CatItem(
                Cat(
                    id = "my_cat",
                    url = "",
                    breeds = listOf(Breed(id = "my_breed", name = "Stray", description = "A simple cat you deserve", temperament = "Playful"))
                )
        )
    }
}