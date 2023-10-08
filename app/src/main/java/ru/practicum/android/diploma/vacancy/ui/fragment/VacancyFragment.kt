package ru.practicum.android.diploma.vacancy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.ui.viewModel.VacancyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.vacancy.ui.VacancyState

class VacancyFragment : Fragment() {

    private val viewModel: VacancyViewModel by viewModel {
        parametersOf(
            vacancyId
        )
    }
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var vacancyProgressBar: ProgressBar? = null
    private var vacancyServerErrorPlaceholder: LinearLayout? = null

    private var vacancyId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacancyId = 87620177

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        vacancyProgressBar = binding.vacancyProgressBar
        vacancyServerErrorPlaceholder = binding.vacancyServerErrorPlaceholder
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Load -> {
                vacancyProgressBar?.visibility = View.VISIBLE
                vacancyServerErrorPlaceholder?.visibility = View.GONE

            }

            is VacancyState.Error -> {
                vacancyProgressBar?.visibility = View.GONE
                vacancyServerErrorPlaceholder?.visibility = View.VISIBLE

            }

            is VacancyState.Content -> {
                vacancyProgressBar?.visibility = View.GONE
                vacancyServerErrorPlaceholder?.visibility = View.GONE

            }
        }
    }
}