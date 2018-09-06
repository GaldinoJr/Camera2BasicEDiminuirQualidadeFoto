package com.example.myapplication.pictureTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.cameraBasic.AutoFitTextureView
import com.example.myapplication.cameraBasic.Camera2BasicFragment
import com.example.myapplication.utils.CompressWhatsapp
import kotlinx.android.synthetic.main.fragment_picture_fragment_test.*


class PictureFragmentTest : Camera2BasicFragment(), Camera2BasicFragment.Listener
{
    lateinit var mByteArray : ByteArray
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_picture_fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadControls()
    }

    private fun loadControls() {
        onCustomResume()
        loadListeners()
    }

    private fun loadListeners() {
        btTakePicture.setOnClickListener {
            lockFocus()
        }
        btCompress.setOnClickListener{
            callCompressPicture()
        }
        setListener(this)
    }

    private fun callCompressPicture()
    {
        context?.let {
            val newByteArray = CompressWhatsapp.compressImage(it, mByteArray)
            context?.let {
                ivPreviewCompress.visibility = View.VISIBLE
                Glide
                        .with(it)
                        .load(newByteArray)
                        .into(ivPreviewCompress)
            }
            tvNewSize.text = "Tamanho novo: " + newByteArray.size
        }

    }

    override fun textureView(): AutoFitTextureView? {
        return autoFitTextureView
    }

    override fun onTakePicture(file: ByteArray)
    {
        mByteArray = file
        tvOldSize.text = "Tamanho normal: " + file.size
        ivPreview.visibility = View.VISIBLE
        context?.let {
            Glide
                .with(it)
                .load(file)
                .into(ivPreview)
        }
    }

    override fun onTakePictureFailed(error: Int) {
        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
    }
}
