import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tugas_p11.Model.User
import com.example.tugas_p11.Model.UserResponse
import com.example.tugas_p11.Network.ApiClient
import com.example.tugas_p11.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var usersContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersContainer = findViewById(R.id.usersContainer)

        fetchUsers()
    }

    private fun fetchUsers() {
        val apiService = ApiClient.apiService
        val call = apiService.getUsers(2)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val users = userResponse?.data ?: emptyList()
                    displayUsers(users)
                } else {
                    Log.e("API Error", "Response Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("API Failure", t.message ?: "Unknown error")
            }
        })
    }

    private fun displayUsers(users: List<User>) {
        for (user in users) {
            // Container untuk satu pengguna
            val userLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 16, 0, 16)
            }

            // Gambar avatar pengguna
            val avatarImageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(150, 150).apply {
                    setMargins(0, 0, 16, 0)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            Picasso.get()
                .load(user.avatar) // Menggunakan Picasso untuk memuat gambar
                .into(avatarImageView)

            // Teks untuk nama dan email pengguna
            val userInfoLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_VERTICAL
            }

            val nameTextView = TextView(this).apply {
                text = "${user.first_name} ${user.last_name}"
                textSize = 18f
                setTextColor(resources.getColor(android.R.color.black, null))
                setPadding(0, 0, 0, 4)
            }

            val emailTextView = TextView(this).apply {
                text = user.email
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.darker_gray, null))
            }

            // Tambahkan TextView ke layout informasi pengguna
            userInfoLayout.addView(nameTextView)
            userInfoLayout.addView(emailTextView)

            // Tambahkan avatar dan informasi pengguna ke layout utama pengguna
            userLayout.addView(avatarImageView)
            userLayout.addView(userInfoLayout)

            // Tambahkan layout pengguna ke container utama
            usersContainer.addView(userLayout)
        }
    }
}
