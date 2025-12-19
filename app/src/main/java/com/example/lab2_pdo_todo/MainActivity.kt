package com.example.lab2_pdo_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons // Імпорт для іконок
import androidx.compose.material.icons.filled.Delete // Імпорт саме іконки смітника
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TodoTask(
    val text: String,
    initialIsDone: Boolean = false
) {
    var isDone by mutableStateOf(initialIsDone)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ToDoScreen(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    val taskList = remember { mutableStateListOf<TodoTask>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ToDo List",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                label = { Text("Додати завдання") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (textState.isNotBlank()) {
                    taskList.add(TodoTask(text = textState))
                    textState = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(taskList) { task ->

                ToDoItemRow(
                    task = task,
                    onDelete = { taskList.remove(task) }
                )
            }
        }
    }
}

@Composable
fun ToDoItemRow(
    task: TodoTask,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { isChecked ->
                    task.isDone = isChecked
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = task.text,
                fontSize = 18.sp,
                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
              
                modifier = Modifier.weight(1f)
            )

            // Кнопка видалення (Смітник)
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Видалити завдання",
                    tint = MaterialTheme.colorScheme.error // Робимо іконку червонуватою (колір помилки/видалення)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoPreview() {
    MaterialTheme {
        ToDoScreen()
    }
}