package com.example.nntran_oblig2.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nntran_oblig2.viewModel.AlpacaViewModel
import com.example.nntran_oblig2.model.AlpacaParty
import com.example.nntran_oblig2.ui.components.AlpacaProfile
import com.example.nntran_oblig2.ui.components.DropDownMenu

@Composable
fun AlpacaScreen(alpacaViewModel : AlpacaViewModel = viewModel()){
    val alpacaUiState by alpacaViewModel.alpacaUiState.collectAsState()

    //val alpacaName = mutableListOf<String>()
    //alpacaUiState.alpacas.forEach { alpacaName.add(it.name) }

    AlpacaView(alpaca = alpacaUiState.alpacas, alpacaViewModel)

}

@Composable
fun AlpacaView(alpaca: List<AlpacaParty>, alpacaViewModel : AlpacaViewModel ){

    Column {
        DropDownMenu(alpacaViewModel)
        LazyColumn(){
            items(alpaca){
                    alpacaData -> AlpacaProfile(alpacaData, alpacaViewModel)
            }
        }
    }

}