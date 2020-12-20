package hamm.android.project.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hamm.android.project.R
import hamm.android.project.databinding.FragmentAppInfoBinding
import hamm.android.project.utils.viewBinding


class AppInfoFragment : Fragment() {

    private val binding by viewBinding(FragmentAppInfoBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.buttonGithub.setOnClickListener { goToUrl("https://github.com/hammasattila/Sapientia-Android2020-Project") }
        binding.buttonGithubApi.setOnClickListener { goToUrl("https://github.com/hammasattila/Sapientia-Android2020-RestAPI") }
        binding.buttonImok.setOnClickListener { goToUrl("https://imok.biz/") }
    }

    private fun goToUrl(url: String) {
        val uriUrl: Uri = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }
}