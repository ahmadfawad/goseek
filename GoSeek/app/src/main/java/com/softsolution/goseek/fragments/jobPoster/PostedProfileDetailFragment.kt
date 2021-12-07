package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.MainActivity
import com.softsolution.goseek.databinding.FragmentPostedProfileDetailBinding
import com.softsolution.goseek.fragments.jobPoster.PostedProfileDetailFragment.Companion.COLOR_SELECTED
import com.softsolution.goseek.fragments.jobPoster.PostedProfileDetailFragment.Companion.NO_COLOR_OPTION
import dev.sasikanth.colorsheet.ColorSheet
import dev.sasikanth.colorsheet.utils.ColorSheetUtils


class PostedProfileDetailFragment : Fragment() {


    companion object {
        private const val COLOR_SELECTED = "selectedColor"
        private const val NO_COLOR_OPTION = "noColorOption"
    }

    private var selectedColor: Int = ColorSheet.NO_COLOR
    private var noColorOption = false


    private var binding: FragmentPostedProfileDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posted_profile_detail, container, false)
        binding!!.setFragment(this)


        binding!!.cardview.setOnClickListener {
            val colors = resources.getIntArray(R.array.colors)
      //      selectedColor = savedInstanceState!!.getInt(COLOR_SELECTED) ?: colors.first()
            setColor(selectedColor)

       //     noColorOption = savedInstanceState!!.getBoolean(NO_COLOR_OPTION) ?: false

//        colorSheet.setOnClickListener {


            ColorSheet().cornerRadius(25)
                .colorPicker(
                    colors = colors,
                    noColorOption = true,
            //        noColorOption = noColorOption,
                    selectedColor = selectedColor,
                    listener = { color ->
                        selectedColor = color
                        setColor(selectedColor)
                    })
                .show(childFragmentManager)
            //   }
            // }

        }



        binding!!.pdfView.getSettings().setJavaScriptEnabled(true)
        binding!!.pdfView.loadUrl("https://drive.google.com/file/d/1kVUijoEKypkaCk2h_F8t7HLPaLCwpPFv/view?usp=sharing")
      //  binding!!.pdfView.loadUrl("")
    //    binding!!.skewPdfView.loadPdf("https://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pf")




        return binding!!.getRoot()
    }
    fun onClick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
            }
        }
    }
  /*  override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COLOR_SELECTED, selectedColor)
        outState.putBoolean(NO_COLOR_OPTION, noColorOption)
    }*/

    private fun setColor(@ColorInt color: Int) {
        if (color != ColorSheet.NO_COLOR) {
            binding!!.profileImage.borderColor=color
        }
    }


}