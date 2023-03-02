package com.example.nntran_oblig2.data

import com.example.nntran_oblig2.model.AlpacaParty
import com.example.nntran_oblig2.model.Party
import com.example.nntran_oblig2.model.Vote

data class AlpacaUiState(val alpacas: List<AlpacaParty>, var district : String){
}

data class VoteUiState(val votes1: List<Vote>, val votes2 : List<Vote>, val votes3 : List<Party>)



