package com.hamoda.peoplerecord.presentation.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hamoda.peoplerecord.R
import com.hamoda.peoplerecord.domain.model.User


@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onDeleteClick: (personId: Int) -> Unit
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = if (user.isMale) R.drawable.ic_boy else R.drawable.ic_girl),
                contentScale = ContentScale.Inside,
                contentDescription = "${user.name}'s avatar"
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = user.jobTitle,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                )

                Row {
                    Text(
                        text = stringResource(R.string.age, user.age),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(R.string.gender, user.gender),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    )
                }
            }

            IconButton(
                onClick = { onDeleteClick(user.id) },
                modifier = Modifier.align(alignment = Alignment.Top)
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
            }
        }
    }
}