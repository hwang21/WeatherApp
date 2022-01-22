package com.goat.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.goat.weatherapp.R
import com.goat.weatherapp.databinding.FragmentMainBinding
import com.goat.weatherapp.model.Weather
import com.goat.weatherapp.utils.Constants.Companion.REQUEST_CODE_LOCATION_PERMISSION
import com.goat.weatherapp.utils.ResponseState
import com.goat.weatherapp.utils.TimeUtil
import com.goat.weatherapp.utils.TrackingUtil
import com.goat.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        requestPermissions()

        binding.weatherCard.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, DetailFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    private fun getWeather() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.weatherState.also {
                mainViewModel.getWeather()
            }
                .collectLatest {
                    when (it) {
                        is ResponseState.Loading -> {
                            binding.progressBarMain.visibility = View.VISIBLE
                        }
                        is ResponseState.Success -> {
                            val weather = it.data
                            bindView(weather)
                            mainViewModel.hourlyWeatherState.value = weather.hourly.data
                            binding.progressBarMain.visibility = View.GONE
                            binding.lowText.visibility = View.VISIBLE
                            binding.highText.visibility = View.VISIBLE
                        }
                        is ResponseState.Error -> {
                            binding.progressBarMain.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
        }
    }

    private fun bindView(weather: Weather) {
        binding.apply {
            val daily = weather.daily.data[0]
            weatherIcon.setIconResource(
                getString(
                    requireActivity().resources.getIdentifier(
                        "wi_forecast_io_" + daily.icon.replace("-", "_"), "string",
                        requireActivity().packageName
                    )
                )
            )
            timeZone.text = weather.timezone
            dateAndTime.text = TimeUtil.convertDate(weather.currently.time)
            currentCondition.text = weather.currently.icon
            highTemp.text = requireActivity()
                .getString(R.string.temp, daily.apparentTemperatureHigh.toString())
            lowTemp.text = requireActivity()
                .getString(R.string.temp, daily.apparentTemperatureLow.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun requestPermissions() {
        if(TrackingUtil.hasLocationPermissions(requireContext())) {
            getWeather()
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getWeather()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (requestCode == REQUEST_COARSE_LOCATION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            getWeather()
        }
    }

    companion object {
        private const val REQUEST_COARSE_LOCATION = 99
        fun newInstance() = MainFragment()
    }
}