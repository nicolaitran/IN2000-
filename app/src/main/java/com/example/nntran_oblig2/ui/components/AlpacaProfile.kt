package com.example.nntran_oblig2.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.nntran_oblig2.R
import com.example.nntran_oblig2.model.AlpacaParty
import com.example.nntran_oblig2.model.Party
import com.example.nntran_oblig2.model.Vote
import com.example.nntran_oblig2.viewModel.AlpacaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlpacaProfile(alpaca : AlpacaParty, alpacaViewModel : AlpacaViewModel = viewModel()){
    val voteUiState by alpacaViewModel.voteUiState.collectAsState()
    val alpacaUiState by alpacaViewModel.alpacaUiState.collectAsState()
    val voteList1 : List<Vote> = voteUiState.votes1
    val voteMap1 = alpacaViewModel.countVote(voteList1)
    val voteList2 : List<Vote> = voteUiState.votes2
    val voteMap2 = alpacaViewModel.countVote(voteList2)
    val voteList3 = voteUiState.votes3
    val voteList3Sum = voteUiState.votes3.sumOf<Party>{ it.votes!! }
    val voteMap3 = alpacaViewModel.makeListToMap(voteList3)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .background(Color(alpaca.color.toColorInt()))
            )
            Text(alpaca.name)

            Image(
                painter = rememberAsyncImagePainter(alpaca.img),
                contentDescription = stringResource(R.string.bildetekst),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)

            )

            Text(alpaca.leader)

            if(voteList1.isNotEmpty() && alpacaUiState.district == stringResource(R.string.d1)){
                val stemmer = (voteMap1.get(alpaca.id)!!/voteList1.size.toFloat()*100)
                val prosent = String.format("%.1f", stemmer)
                Text("Votes: ${voteMap1.get(alpaca.id)} (${prosent} %)")
            }
            if(voteList2.isNotEmpty() && alpacaUiState.district == stringResource(R.string.d2)){
                val stemmer = (voteMap2.get(alpaca.id)!!/voteList2.size.toFloat()*100)
                val prosent = String.format("%.1f", stemmer)
                Text("Votes: ${voteMap2.get(alpaca.id)} (${prosent} %)")
            }
            if(voteList3.isNotEmpty() && alpacaUiState.district == stringResource(R.string.d3)){
                val stemmer = (voteMap3.get(alpaca.id)!!/voteList3Sum.toFloat()*100)
                val prosent = String.format("%.1f", stemmer)
                Text("Votes: ${voteMap3.get(alpaca.id)} (${prosent} %)")
            }
            if(voteList1.isNotEmpty() && voteList2.isNotEmpty() && voteList3.isNotEmpty() && alpacaUiState.district == stringResource(
                                R.string.alle)
                        ){
                val map : Map<String,Int> = alpacaViewModel.combineMaps(voteMap1,voteMap2,voteMap3)
                Text("Votes: ${map.get(alpaca.id)}")
            }


        }
    }
}

