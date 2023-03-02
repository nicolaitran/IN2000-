package com.example.nntran_oblig2.data

import android.util.Xml
import com.example.nntran_oblig2.model.Party
import nl.adaptivity.xmlutil.core.impl.multiplatform.IOException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.InputStream

private val ns: String? = null

class XMLparser{

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Party> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Party> {
        val partyList = mutableListOf<Party>()

        parser.require(XmlPullParser.START_TAG, ns, "districtThree")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag
            if (parser.name == "party") {
                partyList.add(readEntry(parser))
            } else {
                skip(parser)
            }
        }
        return partyList
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): Party {
        parser.require(XmlPullParser.START_TAG, ns, "party")
        var id : Int? = null
        var votes : Int? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "id" -> id = readAttribute(parser, parser.name).toIntOrNull()
                "votes" -> votes = readAttribute(parser, parser.name).toIntOrNull()

                else -> skip(parser)
            }
        }
        return Party(id, votes)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(parser: XmlPullParser, tag : String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        val id = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return id
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}