package com.example.pracainynierska.ui_view_components.components

import TaskDetailsButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.view_model.LoginViewModel

@Composable
fun TaskDetailsDialog(
    navController: NavController,
    loginViewModel: LoginViewModel,
    task: Task,
    onDismiss: () -> Unit,
    onEdit: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Szczegóły zadania",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xDD1C1C1C),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = task.category,
                        color = Color(0xFF3CB043),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = task.difficulty,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = task.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TaskDetailsButton(
                            text = "Wykonaj",
                            color = Color(0xFF3CB043),
                            onClick = { /* Obsługa wykonania zadania todo */ }
                        )

                        TaskDetailsButton(
                            text = "Edytuj",
                            color = Color(0xFFFFFF00),
                            onClick = onEdit
                        )

                        TaskDetailsButton(
                            text = "Anuluj",
                            color = Color(0xFFFF0000),
                            onClick = {
                                loginViewModel.deleteTask(task.id)
                                //navController.navigate("CalendarsView")
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}
