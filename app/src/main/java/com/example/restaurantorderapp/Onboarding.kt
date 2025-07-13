package com.example.restaurantorderapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun OnboardingScreen(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(vertical = 30.dp)
                .width(220.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF495E57))
                .padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Let's get started",
                color = Color.White,
                fontSize = 35.sp,
                fontFamily = MarkaziTextFamily
            )
            Text(
                text = "Firstly enter your personal details",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = KarlaTextFamily
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input fields
        Column(modifier = Modifier.fillMaxWidth()) {
            InputField(label = "First Name", value = firstName) { firstName = it }
            Spacer(modifier = Modifier.height(25.dp))
            InputField(label = "Last Name", value = lastName) { lastName = it }
            Spacer(modifier = Modifier.height(25.dp))
            InputField(label = "Email", value = email) { email = it }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Register button
        Button(
            onClick = { /* handle register */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .height(56.dp)
        ) {
            Text(text = "Register", color = Color.Black, fontSize = 18.sp)
        }
    }
}

@Composable
fun InputField(label: String, value: String, onChange: (String) -> Unit) {
    Column {
        Text(
            text = label,
            color = Color.DarkGray,
            fontSize = 14.sp,
            fontFamily = KarlaTextFamily,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            placeholder = { Text("Enter your $label") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .background(Color.Transparent), // Let containerColor handle bg
            /*colors = OutlinedTextFieldDefaults.(
                containerColor = Color(0xFFF5F5F5),
                focusedBorderColor = Color(0xFF495E57),
                unfocusedBorderColor = Color(0xFFD1D1D1),
                cursorColor = Color(0xFF495E57),
                placeholderColor = Color.Gray
            )*/
        )

    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview(){
    OnboardingScreen(navController = NavHostController(LocalContext.current))
}