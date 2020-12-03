package hamm.android.project.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import hamm.android.project.model.User

class ProfileFragmentViewModel : ViewModel() {
    var settings: SharedPreferences? = null
        set(settings) {
            settings?.let {
                field = settings
                user = User(
                    settings.getString("user_name", "") ?: "",
                    settings.getString("user_address", "") ?: "",
                    settings.getString("user_phone", "") ?: "",
                    settings.getString("user_email", "") ?: "",
                )
            }
        }
    lateinit var user: User

    fun saveProfile() {
        Log.e("Button", "clicked.")
        settings?.let {
            val editor: SharedPreferences.Editor = it.edit()
            editor.putString("user_name", user.name)
            editor.putString("user_address", user.address)
            editor.putString("user_phone", user.phone)
            editor.putString("user_email", user.email)
            editor.commit()
        }
    }
}