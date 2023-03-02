package com.example.nntran_oblig2.model


data class AlpacaParty (
    val id : String,
    val name: String,
    val leader: String,
    val img: String,
    val color: String)

data class AlpacaPartyList(
    val parties: List<AlpacaParty>
)


