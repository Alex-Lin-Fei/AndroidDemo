package com.example.assassinslist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val TAG = "MemberFragment"
private const val ARG_MEMBER_ID = "member-id"
private const val DIALOG_BIRTHDAY = "dialog-birthday"
private const val REQUEST_DATE = 0
class MemberFragment: Fragment(), DatePickerFragment.Callbacks {
    private lateinit var member: Member

    private lateinit var nameField: EditText

    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var maleRadioButton: RadioButton

    private lateinit var birthdayButton: Button
    private lateinit var informationField: EditText
    private lateinit var deadCheckBox: CheckBox

    private val memberDetailViewModel: MemberDetailViewModel by lazy {
        ViewModelProvider(this)[MemberDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "fragment create")
        member = Member()

        val memberId: UUID = arguments?.getSerializable(ARG_MEMBER_ID) as UUID
        Log.d(TAG, "args bundle member ID: $memberId")
        memberDetailViewModel.loadMember(memberId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_member, container, false)

        nameField = view.findViewById(R.id.member_name) as EditText
        genderRadioGroup = view.findViewById(R.id.radio_gender) as RadioGroup
        femaleRadioButton = view.findViewById(R.id.radio_button_female) as RadioButton
        maleRadioButton = view.findViewById(R.id.radio_button_male) as RadioButton

        birthdayButton = view.findViewById(R.id.member_birthday) as Button
        informationField = view.findViewById(R.id.member_information) as EditText
        deadCheckBox = view.findViewById(R.id.member_dead) as CheckBox


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memberDetailViewModel.memberLiveDate.observe(viewLifecycleOwner,
            { member ->
                member?.let {
                    this.member = member
                    updateUI()
                }
            })
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


        birthdayButton.setOnClickListener {
            DatePickerFragment.newInstance(member.birthday).apply {
                setTargetFragment(this@MemberFragment, REQUEST_DATE)
                show(this@MemberFragment.requireFragmentManager(), DIALOG_BIRTHDAY)
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

    override fun onStop() {
        super.onStop()
        memberDetailViewModel.saveMember(member)
    }

    override fun onDateSelected(date: Date) {
        member.birthday = date
        updateUI()
    }

    private fun updateUI() {
        nameField.setText(member.name)
        informationField.setText(member.information)
        birthdayButton.text = member.birthday.toString()
        member.gender = if (femaleRadioButton.isChecked) Gender.FEMALE else Gender.MALE
        deadCheckBox.isChecked = member.dead
    }

    companion object {
        fun newInstance(memberId: UUID): MemberFragment {
            val args = Bundle().apply {
                putSerializable(ARG_MEMBER_ID, memberId)
            }
            return MemberFragment().apply {
                arguments = args
            }
        }
    }
}