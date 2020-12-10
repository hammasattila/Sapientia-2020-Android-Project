package hamm.android.project.utils

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import hamm.android.project.R
import hamm.android.project.viewmodels.MainActivityViewModel
import kotlin.math.abs

fun delayedCheckForLoading(mMainActivityViewModel: MainActivityViewModel?, delay: Long = 2000, action: () -> Unit = {}) {
    mMainActivityViewModel?.let {
        Handler(Looper.getMainLooper()).postDelayed({
            if (!it.loading) {
                action()
            } else {
                delayedCheckForLoading(it, delay, action)
            }
        }, delay)
    }
}

fun ImageView.load(url: String, onLoadingFinished: () -> Unit = {}) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.placeholder_restaurant))
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                Toast.makeText(context, "Failed to load the image", Toast.LENGTH_SHORT).show()
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                onLoadingFinished()
                return false
            }
        })
        .into(this)
}

fun ImageView.load(url: String?, placeholder: Int = R.drawable.placeholder_restaurant) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder))
        .into(this)
}

fun ImageView.load(id: Int, placeholder: Int = R.drawable.placeholder_restaurant) {
    Glide.with(this)
        .load(id)
        .apply(RequestOptions.placeholderOf(placeholder))
        .into(this)
}

fun getClosestInt(to: Int, `in`: List<Int>): Int {
    var min = Int.MAX_VALUE
    var closest = to
    for (v in `in`) {
        val diff = abs(v - to)
        if (diff < min) {
            min = diff
            closest = v
        }
    }

    return closest
}

fun getClosestString(to: String, `in`: List<String>): String {
    var min = Int.MAX_VALUE
    var closest = to
    for (v in `in`) {
        val diff = calculateLevenshteinDistance(v, to) //abs(v - to)
        if (diff < min) {
            min = diff
            closest = v
        }
    }

    return closest
}

private fun costOfSubstitution(a: Char, b: Char): Int {
    return if (a == b) 0 else 1
}

/*
*
* In this article, we describe the Levenshtein distance, alternatively known as the Edit distance.
* https://www.baeldung.com/java-levenshtein-distance
* The algorithm explained here was devised by a Russian scientist, Vladimir Levenshtein, in 1965.
*
* */
fun calculateLevenshteinDistance(a: String, b: String): Int {
    val dp = Array(a.length + 1) { IntArray(b.length + 1) }
    for (i in 0..a.length) {
        for (j in 0..b.length) {
            dp[i][j] = when {
                i == 0 -> j
                j == 0 -> i
                else -> Math.min(
                    Math.min(
                        dp[i - 1][j - 1] + costOfSubstitution(a[i - 1], b[j - 1]),
                        dp[i - 1][j] + 1
                    ),
                    dp[i][j - 1] + 1
                )
            }
        }
    }

    return dp[a.length][b.length]
}
