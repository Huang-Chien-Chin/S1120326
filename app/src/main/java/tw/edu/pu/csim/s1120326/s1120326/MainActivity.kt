package tw.edu.pu.csim.s1120326.s1120326

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.edu.pu.csim.s1120326.s1120326.ui.theme.S1120326Theme
import kotlin.random.Random


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
fun Start(m: Modifier) {
    val context = LocalContext.current

    // 定義背景顏色列表
    val colors = listOf(
        Color(0xff95fe95), // 顏色 1
        Color(0xfffdca0f), // 顏色 2
        Color(0xfffea4a4), // 顏色 3
        Color(0xffa5dfed)  // 顏色 4
    )

    // 當前顏色索引
    var currentIndex by remember { mutableStateOf(0) }
    var isSwiping by remember { mutableStateOf(false) }

    // 計時器
    var timeElapsed by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }

    // 瑪利亞圖示位置
    var mariaPosition by remember { mutableStateOf(0f) }

    // 隨機瑪利亞圖示
    var mariaImage by remember { mutableStateOf(R.drawable.maria0) }

    // 分數
    var score by remember { mutableStateOf(0) }

    // 獲取螢幕配置和密度
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp // 螢幕寬度 (dp 為單位)
    val density = LocalDensity.current.density // 螢幕密度 (dp 到 px 的轉換)

    // 啟動 Coroutine 進行計時和圖示移動
    LaunchedEffect(gameOver) {
        // 如果遊戲尚未結束，開始計時和移動圖示
        if (!gameOver) {
            while (!gameOver) {
                delay(1000) // 每秒鐘執行一次
                if (!gameOver) {
                    timeElapsed += 1 // 每秒鐘增加遊戲時間
                    mariaPosition += 50f // 每秒鐘圖示向右移動

                    // 當瑪利亞圖示完全移出螢幕右邊界，遊戲結束
                    if (mariaPosition > (screenWidth * density)) { // 使用螢幕寬度進行判斷
                        gameOver = true
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[currentIndex]) // 根據索引設置背景顏色
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (!isSwiping) {
                        isSwiping = true
                        change.consume() // 消耗滑動事件
                        if (dragAmount > 0) { // 右滑
                            currentIndex = (currentIndex - 1 + colors.size) % colors.size
                        } else if (dragAmount < 0) { // 左滑
                            currentIndex = (currentIndex + 1) % colors.size
                        }
                        // 延遲一段時間，避免快速滑動
                        kotlinx.coroutines.GlobalScope.launch {
                            kotlinx.coroutines.delay(500) // 延遲500毫秒
                            isSwiping = false
                        }
                    }
                }
            }
            // 檢測雙擊事件
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        val currentBackgroundColor = colors[currentIndex]
                        if (currentBackgroundColor == colors[currentIndex]) {
                            // 背景顏色相同，加1分，重新隨機產生瑪利亞圖示
                            score += 1
                            mariaImage = when (Random.nextInt(4)) {
                                0 -> R.drawable.maria0
                                1 -> R.drawable.maria1
                                2 -> R.drawable.maria2
                                else -> R.drawable.maria3
                            }
                            mariaPosition = 0f // 每次雙擊後，讓圖示從螢幕左邊開始移動
                        } else {
                            // 背景顏色不同，扣1分
                            score -= 1
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "2024期末上機考(資管二A黃千津)",
                style = TextStyle(fontSize = 10.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 顯示圖片，保持原來的大小和位置
            Image(
                painter = painterResource(id = R.drawable.class_a), // 確保圖片存在於 res/drawable 目錄下
                contentDescription = "Class Image",
                modifier = Modifier
                    .fillMaxWidth() // 讓圖片寬度填滿版面
                    .wrapContentHeight(), // 根據圖片比例自動調整高度
                contentScale = ContentScale.Fit // 確保圖片完整顯示，不裁剪
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 顯示「遊戲持續時間」
            Text(
                text = "遊戲持續時間 ${timeElapsed} 秒",
                style = TextStyle(fontSize = 10.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "您的成績 $score 分",
                style = TextStyle(fontSize = 10.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.height(32.dp))

            // 結束遊戲的按鈕
            Button(
                onClick = {
                    // 使用 LocalContext.current 並檢查是否為 Activity
                    val activity = context as? Activity
                    activity?.finish() // 如果是 Activity，則結束它
                },
                modifier = Modifier
                    .padding(top = 1.dp)
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium.copy(CornerSize(16.dp))
            ) {
                Text(text = "結束App")
            }
        }

        // 瑪利亞圖示
        if (!gameOver) {
            Image(
                painter = painterResource(id = mariaImage), // 使用隨機的瑪利亞圖示
                contentDescription = "Maria",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = mariaPosition.dp, y = 0.dp)
                    .size(200.dp)
            )
        }
    }
}