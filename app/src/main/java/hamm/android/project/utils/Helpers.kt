package hamm.android.project.utils

import kotlin.math.abs

    object Helpers {
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
    }
