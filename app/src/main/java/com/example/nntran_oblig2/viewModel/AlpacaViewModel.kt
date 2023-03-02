package com.example.nntran_oblig2.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nntran_oblig2.data.*
import com.example.nntran_oblig2.model.Party
import com.example.nntran_oblig2.model.Vote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlpacaViewModel : ViewModel(){

    private var district by mutableStateOf("")
    private val dataSource = AlpacaDataSource("https://www.uio.no/studier/emner/matnat/ifi/IN2000/v23/obligatoriske-oppgaver/alpacaparties.json")

    private val _alpacaUiState = MutableStateFlow(AlpacaUiState(listOf(),district))
    val alpacaUiState : StateFlow<AlpacaUiState> = _alpacaUiState.asStateFlow()

    private val _voteUiState = MutableStateFlow(VoteUiState(mutableListOf(), mutableListOf(),mutableListOf()))
    val voteUiState : StateFlow<VoteUiState> = _voteUiState.asStateFlow()

    private val d1_dataSource = VoteDataSource("https://in2000-proxy.ifi.uio.no/alpacaapi/district1")
    private val d2_dataSource = VoteDataSource("https://in2000-proxy.ifi.uio.no/alpacaapi/district2")
    private val d3_dataSource = VoteDataSourceXML("https://in2000-proxy.ifi.uio.no/alpacaapi/district3")


    init{
        loadAlpaca()
        loadVote(d1_dataSource,d2_dataSource,d3_dataSource)
    }

    private fun loadAlpaca(){
        viewModelScope.launch{
            val alpaca = dataSource.fetchAlpaca()
            _alpacaUiState.value = AlpacaUiState(alpaca, " ")
        }
    }

    private fun loadVote(dataSource_d1: VoteDataSource,dataSource_d2: VoteDataSource, dataSource_d3: VoteDataSourceXML){
        //var allVotes : List<List<Vote>>
        viewModelScope.launch {
            val votes1 = dataSource_d1.fetchVote()
            val votes2 = dataSource_d2.fetchVote()
            val votes3 = dataSource_d3.fetchVote()

            _voteUiState.value = VoteUiState(votes1,votes2,votes3)

        }
    }

    fun countVote(voteList : List<Vote>) : Map<String,Int>{
        val idCount = mutableMapOf<String,Int>()
        voteList.forEach {
            if(idCount.containsKey(it.id)){
                idCount[it.id] = idCount[it.id]!! +1
            }else{
                idCount[it.id] = 1
            }
        }
        return idCount
    }


    fun updateDistrict(district : String){
        _alpacaUiState.value = alpacaUiState.value.copy(_alpacaUiState.value.alpacas,district)
    }

    fun makeListToMap(voteListXMl : List<Party>) : Map<String,Int?>{
        val idCount = mutableMapOf<String,Int?>()
        voteListXMl.forEach {
            idCount.put(it.id.toString(),it.votes)
        }
        return idCount
    }

    fun combineMaps(map1 : Map<String,Int>, map2 : Map<String,Int>, map3 : Map<String,Int?>) : Map<String,Int>{
        val combinedMap = mutableMapOf<String,Int>()
        for (key in map1.keys) {
            if (map2.containsKey(key) && map3.containsKey(key)) {
                val sum = map1.getValue(key) + map2.getValue(key) + map3.getValue(key)!!
                combinedMap[key] = sum
            }
        }
        return combinedMap
    }

}