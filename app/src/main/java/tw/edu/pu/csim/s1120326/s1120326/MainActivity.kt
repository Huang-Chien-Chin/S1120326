package tw.edu.pu.csim.s1120326.s1120326

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tw.edu.pu.csim.s1120326.s1120326.ui.theme.S1120326Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S1120326Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    Start(m = Modifier.padding(innerPadding))

                }
            }
        }
    }
}


@Composable
fun Start(m: Modifier = Modifier) {
    // 整體畫面背景
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff95fe95)), // 背景顏色
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 標題文字
            Text(
                text = "2024期末上機考(資管二A黃千津)",
                style = TextStyle(
                    fontSize = 10.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 圖片
            Image(
                painter = painterResource(id = R.drawable.class_a), // 確保圖片名稱為 `class_a`
                contentDescription = "班級圖片",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 遊戲時間
            Text(
                text = "遊戲持續時間 0 秒",
                style = TextStyle(fontSize = 10.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 成績文字
            Text(
                text = "您的成績 0 分",
                style = TextStyle(fontSize = 10.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 結束 App 按鈕
            val activity = (LocalContext.current as? Activity)
            Button(
                onClick = {
                    activity?.finish()
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text(text = "結束App")
            }
        }
    }
}


