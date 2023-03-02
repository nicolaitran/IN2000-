package com.example.nntran_oblig2.data


import com.example.nntran_oblig2.model.AlpacaParty
import com.example.nntran_oblig2.model.AlpacaPartyList
import com.example.nntran_oblig2.model.Party
import com.example.nntran_oblig2.model.Vote
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.xml.*


class AlpacaDataSource(val path: String) {

    private val client = HttpClient() {

        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchAlpaca(): List<AlpacaParty> {
        val alpaca: AlpacaPartyList = client.get(path).body()

        val parties = alpaca.parties
        return parties
    }
}

class VoteDataSource(val path: String) {

    private val client = HttpClient() {

        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchVote() : List<Vote>{
        val voteList : List<Vote> = client.get(path).body()
        return voteList
    }

}

class VoteDataSourceXML(val path : String){

    val client = HttpClient() {
        install(ContentNegotiation) {
            xml()
        }
    }
    suspend fun fetchVote() : List<Party>{
        val voteList  = XMLparser().parse(client.get(path).body())
        return voteList
    }
}

