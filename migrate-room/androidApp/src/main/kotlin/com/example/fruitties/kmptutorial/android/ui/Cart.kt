package com.example.fruitties.kmptutorial.android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fruitties.kmptutorial.android.model.CartItemWithFruittie

@Composable
fun Cart(
    cartItems: List<CartItemWithFruittie>,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Cart has ${cartItems.count()} items",
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
        )
        Button(onClick = { expanded = !expanded }) {
            Text(text = if (expanded) "collapse" else "expand")
        }
    }
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(animationSpec = tween(1000)),
        exit = fadeOut(animationSpec = tween(1000)),
    ) {
        Column(
            modifier.padding(horizontal = 32.dp),
        ) {
            if (cartItems.isEmpty()) {
                Text("Cart is empty, add some items")
            } else {
                cartItems.forEach { item ->
                    Text(text = "${item.fruittie.name} : ${item.cartItem.count}")
                }
            }
        }
    }
}