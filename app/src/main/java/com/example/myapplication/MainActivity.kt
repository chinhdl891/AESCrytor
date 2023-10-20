package com.example.myapplication

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private var buttonEncode : Button? = null
    private var textDeCodec : EditText? = null
    private var textEncode : EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonEncode = findViewById(R.id.btn_encode)
        textEncode = findViewById(R.id.tv_input)
        textDeCodec = findViewById(R.id.tv_output)

        buttonEncode!!.setOnClickListener {
            val originalString = textEncode!!.text.toString()
            val time = System.currentTimeMillis()
            GlobalScope.launch(Dispatchers.IO) {
                val job = launch(Dispatchers.IO) {
                    Log.d("Original String", originalString)

                    val encryptedString = AESCryptor.encrypt(originalString)
                    Log.d("Encrypted String", encryptedString)

                    val decryptedString = AESCryptor.decrypt(encryptedString)
                    Log.d("Decrypted String", decryptedString)
                }


                withContext(Dispatchers.Main) {
                    job.join()
                    Toast.makeText(this@MainActivity, "Finish", Toast.LENGTH_SHORT).show()
                    val timeSusses = System.currentTimeMillis() - time
                    Log.d("Time progress ", "$timeSusses")
                }
            }
        }

    }
}

object AESCryptor {

    private const val AES_MODE = "AES/CBC/PKCS7Padding"
    private const val CHARSET_NAME = "UTF-8"
    private val AES_KEY = "0123456789abcdef".toByteArray() // Replace with your own AES key
    private val AES_IV = "1234567890abcdef".toByteArray() // Replace with your own AES IV

    fun encrypt(input: String): String {
        return try {
            val secretKeySpec = SecretKeySpec(AES_KEY, "AES")
            val iv = IvParameterSpec(AES_IV)
            val cipher = Cipher.getInstance(AES_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
            val cipherText = cipher.doFinal(input.toByteArray(charset(CHARSET_NAME)))
            Base64.encodeToString(cipherText, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun decrypt(input: String): String {
        return try {
            val secretKeySpec = SecretKeySpec(AES_KEY, "AES")
            val iv = IvParameterSpec(AES_IV)
            val cipher = Cipher.getInstance(AES_MODE)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv)
            val cipherText = cipher.doFinal(Base64.decode(input, Base64.DEFAULT))
            String(cipherText, charset(CHARSET_NAME))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    val content =
        "FLUID_TYPE 1 FORCE 0.000206 INPUT_SIZE 0.037751 TOUCH_INPUT_FORCE 0.121595 TOUCH_INPUT_SIZE 0.206827 INPUT_SWIPE_MODE 0 INPUT_TOUCH_MODE 0 INPUT_SWIPE_CONSTANT false SIM_SPEED 1.000000 VEL_LIFE_TIME 0.215144 SWIRLINESS 0.674951 VEL_NOISE 0.000000 NUM_SOURCES 0 SOURCE_SPEED 0.000500 AUTO_ON_RESUME false NUM_HOLD_SOURCES 0 FLASH_ENABLED false FLASH_FREQUENCY 1.000000 PAINT_ENABLED true FLUID_AMOUNT 0.001069 FLUID_LIFE_TIME 11.179999 COLOR_CHANGE 0 COLOR_OPTION 0 RANDOM_SATURATION 1.000000 COLOR0 15624253 COLOR1 3968490 COLOR2 16711423 COLOR3 -1118482 COLOR4 -1118482 COLOR5 -1118482 COLOR_ACTIVE0 true COLOR_ACTIVE1 true COLOR_ACTIVE2 false COLOR_ACTIVE3 false COLOR_ACTIVE4 false COLOR_ACTIVE5 false DCOLOR0 -48060 DCOLOR1 -12255420 DCOLOR2 -12303105 DCOLOR_ACTIVE0 true DCOLOR_ACTIVE1 true DCOLOR_ACTIVE2 true BACKGROUND_COLOR -1118482 MULTI_COLOR0 0 MULTI_COLOR1 54015 MULTI_COLOR2 14155520 MULTI_COLOR3 16711739 MULTI_COLOR4 0 MULTI_COLOR5 15116346 MULTI_COLOR6 1503083 MULTI_COLOR7 4211181 MULTI_COLOR8 0 MULTI_COLOR_DOUBLE true CARTOON_COLORS false MAGIC_PALETTE_G 0.100000 MAGIC_PALETTE_B 0.200000 MAGIC_PALETTE_BASE_SHIFT 0.000000 MAGIC_PALETTE_BASE_SHIFT_SPEED 0.000000 MAGIC_PALETTE_BLACK_BACKGR false OVERBRIGHT_COLORS true INVERT_COLORS false COLOR_RANDOMNESS 0.000000 PE_EDGE false PE_EDGE_INTENSITY 0.900000 PE_SCATTER false PE_SCATTER_INTENSITY 0.500000 PE_SCATTER_TYPE 0 PE_MULTIIMAGE false PE_MULTIIMAGE_COLORING false PE_MULTIIMAGE_INTENSITY 0.500000 PE_MIRROR false PE_MIRROR_COLORING true PE_MIRROR_TYPE 0 PARTICLES_ENABLED false PARTICLES_MODE 0 PARTICLES_SHAPE 0 PARTICLES_PER_SEC 650.000000 PARTICLES_LIFE_TIME_SEC 15.000000 PARTICLES_SIZE 10.000000 PARTICLES_INTENSITY 0.350000 PARTICLES_SMOOTHNESS 1.000000 PARTICLES_MIXING_MODE 0 PARTICLES_TRAIL_LENGTH 0.500000 BORDER_MODE 0 GRAVITY 0.000000 SHADING_ENABLED false SHADING_BUMPINESS 0.750000 SHADING_FLUID_BRIGHTNESS 1.000000 SHADING_SPEC_TYPE 1 SHADING_SPECULAR 1.300000 SHADING_SPEC_POWER 0.400000 SHADING_SPEC_ONLY_GLOW false GLOW true GLOW_LEVEL_STRENGTH0 0.074380 GLOW_LEVEL_STRENGTH1 0.190083 GLOW_LEVEL_STRENGTH2 0.157025 GLOW_THRESHOLD 0.127066 LIGHT_SOURCE true LIGHT_INTENSITY 1.445000 LIGHT_RADIUS 0.401000 LIGHT_COLOR 14808055 LIGHT_SOURCE_SPEED 0.000000 LIGHT_SOURCE_POSX 0.500000 LIGHT_SOURCE_POSY 0.500000 SHADOW_SOURCE true SHADOW_SELF true SHADOW_INVERSE false SHADOW_INTENSITY 4.885330 SHADOW_FALLOFF_LENGTH 0.350000 USE_DETAIL_TEXTURE false DETAIL_TEXTURE 0 DETAIL_UV_SCALE 2.500000 "

}