import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Login
import com.example.myapplication.LoginService
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var login: Login? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //레이아웃 선언
        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<TextView>(R.id.editText)
        val editText2 = findViewById<TextView>(R.id.editText2)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.10.100:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)



        button.setOnClickListener{
            var text1 = editText.text.toString()
            var text2 = editText2.text.toString()

            loginService.requestLogin(text1,text2).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("LOGIN", it1) }
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    login = response.body()
                    Log.d("LOGIN","msg : "+login?.msg)
                    Log.d("LOGIN","code : "+login?.code)
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle(login?.msg)
                    dialog.setMessage(login?.code)
                    dialog.show()
                }
            })
        }
    }
}