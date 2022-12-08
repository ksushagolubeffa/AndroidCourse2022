package com.example.homework2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2.databinding.ActivityFirstBinding
import com.example.homework2.databinding.ActivitySecondBinding


class FirstActivity : AppCompatActivity() {
    private var binding: ActivityFirstBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFirstBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }
        binding?.bt2?.setOnClickListener{
            startTimer("you need to wash your plate!", 45)
        }
    }

    //здесь метод - отправка сообщения в другое активити через методы,
    // по которым мы взаимодействуем с другими приложениями, прописан в манифесте и у кнопки
    // как onClick в layout
    fun firstClick(view: View){
        val intent = Intent("transfer")
        intent.putExtra(EXTRA_SCORE, "if you see it, you`re a sun!")
        startActivity(intent)
    }

    // метод старта таймера, прописан в манифесте
    fun startTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    //здесь был просто метод, вызывавшийся через onClick у кнопки, можно было скопировать надпись
    fun secondClick(view:View){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "hey! have a good day!")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // метод звонка, не прописан в манифесте, указан у кнопки как onClick в layout
    fun thirdClick(view:View){
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel:89123456789")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    companion object{
      const val EXTRA_SCORE = "extra_score"
     }
}