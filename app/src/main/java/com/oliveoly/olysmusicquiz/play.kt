package com.oliveoly.olysmusicquiz

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector

import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track

class play : AppCompatActivity() {
    private val clientId = "ae06b1a58ab64a2fa5351087d3183277"
    private val redirectUri = "http://com.oliveoly.olysmusicquiz/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
    }
    override fun onStart() {
        super.onStart()
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()
        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")

                connected()
            }



            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)

            }
        })

    }

    private fun connected() {

        spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX0QqahDuqmRY")




    }

    override fun onStop() {
        super.onStop()

    }
    fun deviner(view: View){
        spotifyAppRemote?.playerApi?.skipNext()
        var resultat =findViewById<View>(R.id.champ)
        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            val track: Track = it.track
            if(
                resultat.toString()== track.name.toString()
            ) {
                Log.d("play", track.name + " by " + track.artist.name)
                spotifyAppRemote?.playerApi?.skipNext()
            }
        }


    }
}











