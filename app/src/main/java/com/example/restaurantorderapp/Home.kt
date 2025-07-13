package com.example.restaurantorderapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val focusManager = LocalFocusManager.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedItem by remember { mutableStateOf<Pair<String, String>?>(null) } // name, desc
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Account",
                    tint = Color(0xFF495E57),
                    modifier = Modifier.width(40.dp).aspectRatio(1f)
                        .clickable { navController.navigate("profile") }
                )

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .width(220.dp)
                )

                Spacer(modifier = Modifier.width(40.dp).aspectRatio(1f))
            }

            Hero()

            val categories = listOf("all", "starters", "mains", "desserts", "sides")
            var selectedCategory by remember { mutableStateOf("all") }

            FilterSection(
                categories = categories,
                selected = selectedCategory,
                onSelect = { selectedCategory = it }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
            )

            val sampleMenuItems = listOf(
                Triple(
                    "Greek Salad",
                    "Fresh lettuce, tomatoes, cucumbers, olives, and feta cheese.",
                    "$12.99"
                ) to R.drawable.hero
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(sampleMenuItems) { (info, imageRes) ->
                    MenuItemCard(
                        name = info.first,
                        description = info.second,
                        price = info.third,
                        imageRes = imageRes,
                        onClick = {
                            selectedItem = Pair(info.first, info.second)
                            scope.launch { sheetState.show() }
                        }
                    )
                }
            }

        }
    }

    if (selectedItem != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedItem = null },
            sheetState = sheetState,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            dragHandle = { } // You can customize drag handle or leave it empty
        ) {
            MenuItemDetailsModal(
                name = selectedItem!!.first,
                description = selectedItem!!.second,
                imageRes = R.drawable.hero, // or pass dynamically
            )
        }
    }
}

@Composable
fun MenuItemCard(
    name: String,
    description: String,
    price: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable{onClick()},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = price,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF495E57)
            )
        }

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Menu item image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MenuItemDetailsModal(
    name: String,
    description: String,
    imageRes: Int
) {
    var quantity by remember { mutableIntStateOf(1) }

    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Column {

            // Large image
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(name, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            Text(description, fontSize = 16.sp, color = Color.Gray)

            Spacer(Modifier.height(16.dp))

            // Counter
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Text("-", fontSize = 28.sp, modifier = Modifier
                    .clickable { if (quantity > 1) quantity-- }
                    .padding(16.dp))
                Text(quantity.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("+", fontSize = 20.sp, modifier = Modifier
                    .clickable { quantity++ }
                    .padding(16.dp))
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { /* Add to order */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Add to Order", fontSize = 18.sp, color = Color.Black)
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            "Order for delivery",
            color = Color.DarkGray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            "Click on item to add it to the basket",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(categories) { category ->
                AssistChip(
                    onClick = { onSelect(category) },
                    label = {
                        Text(
                            text = category.replaceFirstChar { it.uppercase() },
                            fontSize = 14.sp
                        )
                    },
                    shape = RoundedCornerShape(50),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (selected == category) Color(0xFFF4CE14) else Color(0xFFEFEFEF),
                        labelColor = if (selected == category) Color.Black else Color.DarkGray
                    )
                )
            }
        }
    }
}

@Composable
fun Hero(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF495E57))
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        // Title + Location

        Text(
            text = "Little Lemon",
            color = Color(0xFFF4CE14),
            fontSize = 40.sp,
            fontFamily = MarkaziTextFamily
        )




        // Description + Image
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier.weight(1f)) {
                Text(
                    text = "Chicago",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = MarkaziTextFamily
                )

                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = KarlaTextFamily,
                    lineHeight = 22.sp,
                    modifier = Modifier

                        .padding(end = 16.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hero),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Styled Search Input
        OutlinedTextField(
            value = "",
            onValueChange = { /**/ },
            placeholder = { Text("Search...") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
            ,
            textStyle = TextStyle(color = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                focusedContainerColor = Color.White.copy(alpha = 0.1f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                focusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color.White,
                cursorColor = Color(0xFFF4CE14)
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(navController = NavHostController(LocalContext.current))
}