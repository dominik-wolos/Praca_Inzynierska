package com.example.pracainynierska.ui_view_components.view

import BottomMenu
import android.annotation.SuppressLint
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.pracainynierska.R
import com.example.pracainynierska.model.Task
import com.example.pracainynierska.ui.components.ModalDrawer
import com.example.pracainynierska.ui_view_components.components.TaskCard
import com.example.pracainynierska.ui_view_components.components.TaskDetailsDialog
import com.example.pracainynierska.ui_view_components.components.TopMenu
import com.example.pracainynierska.view_model.LoginViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarsView(navController: NavController, loginViewModel: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    val selectedDate = remember { mutableStateOf("") }
    val selectedTasks = remember { mutableStateOf<List<Task>?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    ModalDrawer(navController = navController, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopMenu(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    drawerState = drawerState,
                    onDrawerOpen = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomMenu(navController = navController)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF4C0949), Color(0xFF470B93)),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        )
                    )
                    .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(55.dp))

                    AndroidView(
                        factory = { context ->
                            val locale = Locale("pl", "PL")
                            Locale.setDefault(locale)
                            val config = context.resources.configuration
                            config.setLocale(locale)
                            val localizedContext = context.createConfigurationContext(config)

                            CalendarView(ContextThemeWrapper(localizedContext, R.style.CustomCalendarView)).apply {
                                firstDayOfWeek = Calendar.MONDAY
                                setOnDateChangeListener { _, year, month, dayOfMonth ->
                                    val selectedCalendar = Calendar.getInstance().apply {
                                        set(year, month, dayOfMonth)
                                    }

                                    // Format daty
                                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    selectedDate.value = dateFormat.format(selectedCalendar.time)

                                    // Pobranie zadania dla wybranej daty
                                    selectedTasks.value = loginViewModel.getTasksForDate(selectedDate.value)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    selectedTasks.value?.let { tasks ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 16.dp)
                        ) {
                            tasks.forEach { task ->

                                TaskCard(
                                    task = task,
                                    onClick = { selectedTask = task }
                                )
                            }
                            Spacer(modifier = Modifier.height(64.dp))
                        }
                    }
                }
                selectedTask?.let { task ->
                    TaskDetailsDialog(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        task = task,
                        onDismiss = { selectedTask = null },
                        onEdit = {
                            navController.navigate("EditTaskView/${task.id}")
                        }
                    )
                }
            }
        }
    }
}
