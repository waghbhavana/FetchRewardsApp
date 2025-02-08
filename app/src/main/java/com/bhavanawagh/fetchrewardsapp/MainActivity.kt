package com.bhavanawagh.fetchrewardsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.bhavanawagh.fetchrewardsapp.api.NetworkResponse
import com.bhavanawagh.fetchrewardsapp.data.ListResponseItem
import com.bhavanawagh.fetchrewardsapp.screens.ErrorMessage
import com.bhavanawagh.fetchrewardsapp.screens.ListScreen
import com.bhavanawagh.fetchrewardsapp.screens.LoadingIndicator
import com.bhavanawagh.fetchrewardsapp.ui.theme.FetchRewardsAppTheme
import com.bhavanawagh.fetchrewardsapp.viewmodels.ListDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val listDataViewModel: ListDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            FetchRewardsAppTheme {
                when (val resultData = listDataViewModel.responseData.collectAsState().value) {

                    is NetworkResponse.Loading -> {
                        LoadingIndicator()
                    }

                    is NetworkResponse.Success -> {
                        val list = resultData.data
                        ListScreen(list = list as List<ListResponseItem>)
                    }

                    is NetworkResponse.Error -> {
                        ErrorMessage(message = resultData.message)
                    }
                }
            }
        }
    }
}

