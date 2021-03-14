package com.example.assassinslist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

private const val TAG = "MemberFragment"
class MemberFragment: Fragment() {
    private lateinit var member: Member

    private lateinit var nameField: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var birthdayButton: Button
    private lateinit var informationField: EditText
    private lateinit var deadCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "fragment create")
        member = Member()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_member, container, false)

        nameField = view.findViewById(R.id.member_name) as EditText
        genderRadioGroup = view.findViewById(R.id.radio_gender) as RadioGroup
        birthdayButton = view.findViewById(R.id.member_birthday) as Button
        informationField = view.findViewById(R.id.member_information) as EditText
        deadCheckBox = view.findViewById(R.id.member_dead) as CheckBox

        birthdayButton.apply {
            text = member.birthday.toString()
            isEnabled = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        val nameWatcher =  object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                member.name = p0.toString()
                Log.d(TAG, member.name)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

        nameField.addTextChangedListener(nameWatcher)



        genderRadioGroup.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
            run {
                when (i) {
                    R.id.radio_button_male -> member.gender = Gender.MALE
                    else -> member.gender = Gender.FEMALE
                }
                Log.d(TAG, member.gender.toString())
            }
        }


        val informationWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(TAG, "before")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                member.information = p0.toString()
                Log.d(TAG, member.information)
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d(TAG, "after")
            }
        }

        informationField.addTextChangedListener(informationWatcher)

        deadCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                member.dead = isChecked
            }
        }

    }
}