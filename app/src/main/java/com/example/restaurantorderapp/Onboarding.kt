package com.example.restaurantorderapp


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun HandleRegister(firstName: String, lastName: String, email: String, isProfile: Boolean, navController: NavHostController){
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("AppData", Context.MODE_PRIVATE)
    prefs.edit {
        putString("firstName", firstName)
        putString("lastName", lastName)
        putString("email", email)
    }
    if(!isProfile){
        navController.navigate("home")
        Toast.makeText(LocalContext.current, "Personal information were saved successfully", Toast.LENGTH_SHORT).show()

    }else{
        Toast.makeText(LocalContext.current, "Personal information were changed successfully", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val sharedPreferences = LocalContext.current.getSharedPreferences("AppData", Context.MODE_PRIVATE)

    val firstNameRaw = sharedPreferences.getString("firstName", "") ?: ""
    val lastNameRaw = sharedPreferences.getString("lastName", "") ?: ""
    val emailRaw = sharedPreferences.getString("email", "") ?: ""

    var firstName by remember { mutableStateOf(firstNameRaw) }
    var lastName by remember { mutableStateOf(lastNameRaw) }
    var email by remember { mutableStateOf(emailRaw) }
    var isRegister by remember { mutableStateOf(false) }
    var isLogout by remember { mutableStateOf(false) }
    var isProfile by remember { mutableStateOf(firstNameRaw.isNotEmpty() && lastNameRaw.isNotEmpty() && emailRaw.isNotEmpty()) }

    if(isRegister){
        if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()){
            HandleRegister(firstName, lastName, email, isProfile, navController)
        }else {
            Toast.makeText(LocalContext.current, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            isRegister = false
        }
    }

    if(isLogout){
        val context = LocalContext.current
        val prefs = context.getSharedPreferences("AppData", Context.MODE_PRIVATE)
        prefs.edit {
            remove("firstName")
            remove("lastName")
            remove("email")
        }
        navController.navigate("home")
    }


    val focusManager = LocalFocusManager.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Top Row (Home + Logo)

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = if (isProfile) Arrangement.SpaceBetween else Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isProfile) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color(0xFF495E57),
                            modifier = Modifier.width(40.dp).aspectRatio(1f)
                                .clickable { navController.navigate("home") }
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .width(220.dp)
                    )

                    if (isProfile) {
                        Spacer(modifier = Modifier.width(40.dp).aspectRatio(1f))
                    }
                }
            }

            item {
                // Title Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF495E57))
                        .padding(vertical = 35.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isProfile) "Your profile information" else "Let's get started",
                        color = Color.White,
                        fontSize = 35.sp,
                        fontFamily = MarkaziTextFamily
                    )
                    Text(
                        text = if (isProfile) "You can change your personal details or log out" else "Firstly enter your personal details",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = KarlaTextFamily,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            item {
                // Input fields
                Column(modifier = Modifier.fillMaxWidth()) {
                    InputField(label = "First Name", value = firstName) { firstName = it }
                    Spacer(modifier = Modifier.height(25.dp))
                    InputField(label = "Last Name", value = lastName) { lastName = it }
                    Spacer(modifier = Modifier.height(25.dp))
                    InputField(label = "Email", value = email) { email = it }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }

            item {
                // Register button
                Button(
                    onClick = { isRegister = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = if (isProfile) "Save changes" else "Register",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
            }

            item {
                if (isProfile) {
                    Button(
                        onClick = { isLogout = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEB5757)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                            .height(56.dp)
                    ) {
                        Text(text = "Log out", color = Color.White, fontSize = 18.sp)
                    }
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InputField(label: String, value: String, onChange: (String) -> Unit) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

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
                .background(Color.Transparent)
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
        )

    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview(){
    OnboardingScreen(navController = NavHostController(LocalContext.current))
}