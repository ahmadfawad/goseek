package com.softsolution.goseek.fragments.jobSeeker

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.softsolution.goseek.R
import com.softsolution.goseek.databinding.FragmentOptionsBinding
import com.softsolution.goseek.utils.Constants.Companion.login

class OptionsFragment : Fragment(), View.OnClickListener {
    private var binding: FragmentOptionsBinding? = null
    var newUser: RadioButton? = null
    var currentUser:RadioButton? = null
    var userNew: MaterialButton? = null
    var userCurrent:MaterialButton? = null
    var dialog: BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_options, container, false)
        binding!!.setFragment(this)


        binding!!.existingUser.setBackgroundTintList(
            ContextCompat.getColorStateList(
                requireActivity(),
                R.color.white
            )
        )
        binding!!.existingUser.setOnClickListener(this)
        binding!!.newUser.setOnClickListener(this)
        binding!!.existingUser.setTextColor(Color.parseColor("#080808"))


        return binding!!.getRoot()

    }

    override fun onClick(view: View?) {

       val navController = findNavController()
        when (view?.id) {
            R.id.radia_id1 -> {
                currentUser!!.isChecked = false
                newUser!!.isChecked = true
                newUser!!.setTextColor(Color.parseColor("#F87062"))
                currentUser!!.setTextColor(Color.parseColor("#080808"))
                // startActivity(Intent(getApplicationContext(), JobSeekerProfileActivity::class.java))

                Handler().postDelayed({
                    dialog!!.dismiss()
                    navController.navigate(R.id.action_optionsFragment_to_newJobSeekerFragment)
                }, 750)

            }
            R.id.radia_id2 -> {
                newUser!!.isChecked = false
                currentUser!!.isChecked = true
                currentUser!!.setTextColor(Color.parseColor("#F87062"))
                newUser!!.setTextColor(Color.parseColor("#080808"))
                // startActivity(Intent(getApplicationContext(), NewJobSeekerActivity::class.java))
                Handler().postDelayed({
                    dialog!!.dismiss()
                    navController.navigate(R.id.action_optionsFragment_to_newJobPosterFragment)
                }, 750)

            }
            R.id.newUser -> {
                showBottomSheetDialog()
                binding!!.newUser.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.white
                    )
                )
                binding!!.newUser.setTextColor(Color.parseColor("#080808"))
                binding!!.existingUser.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.transparent
                    )
                )
                binding!!.existingUser.setTextColor(Color.parseColor("#FFFFFFFF"))

                //navController.navigate(R.id.action_optionsFragment_to_newJobSeekerFragment)
            }
            R.id.existingUser -> {
                binding!!.existingUser.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.white
                    )
                )
                binding!!.existingUser.setTextColor(Color.parseColor("#080808"))
                binding!!.newUser.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.transparent
                    )
                )
                binding!!.newUser.setTextColor(Color.parseColor("#FFFFFFFF"))
                navController.navigate(R.id.action_optionsFragment_to_loginFragment)
                //startActivity(Intent(getApplicationContext(), DashbordActivity::class.java))
            }
        }

    }


    private fun showBottomSheetDialog() {

        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)

        dialog = BottomSheetDialog(requireActivity(), R.style.DialogCustomTheme)
        dialog!!.setContentView(view)
        newUser = dialog!!.findViewById(R.id.radia_id1)
        newUser!!.setOnClickListener(this)
        currentUser = dialog!!.findViewById(R.id.radia_id2)
        currentUser!!.setOnClickListener(this)
        dialog!!.show()
    }
}