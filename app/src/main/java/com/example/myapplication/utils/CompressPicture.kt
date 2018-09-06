package com.example.myapplication.utils

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*


class CompressPicture
{
    companion object {
        fun decodeFile(f: File): File
        {
            var b: Bitmap? = null

            //Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true

            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(f)
                BitmapFactory.decodeStream(fis, null, o)
                fis!!.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val IMAGE_MAX_SIZE = 1024
            var scale = 1
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = Math.pow(2.0, Math.ceil(Math.log(IMAGE_MAX_SIZE / Math.max(o.outHeight, o.outWidth).toDouble()) / Math.log(0.5)).toInt().toDouble()).toInt()
            }

            //Decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            try {
                fis = FileInputStream(f)
                b = BitmapFactory.decodeStream(fis, null, o2)
                fis!!.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Log.d(TAG, "Width :" + b!!.width + " Height :" + b.height)

            var destFile = File(f, "img_"
//                + dateFormatter.format(Date()).toString() + ".png")
                    + "teste" + ".png")
            try {
                val out = FileOutputStream(destFile)
                b.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return destFile
        }

        fun byteArrayToBitmap(byteArray: ByteArray): Bitmap
        {
            val options = BitmapFactory.Options()
            options.inMutable = true
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
        }

        fun byteArrayToFile(byteArray: ByteArray) : File
        {
//            val file = File("myfile.txt")

            // convert File to byte[]
//            val bos = ByteArrayOutputStream()
//            val oos = ObjectOutputStream(bos)
//            oos.writeObject(file)
//            bos.close()
//            oos.close()
//            val bytes = bos.toByteArray()

            // convert byte[] to File
            val bis = ByteArrayInputStream(byteArray)
            val ois = ObjectInputStream(bis)
            val fileFromBytes = ois.readObject() as File
            bis.close()
            ois.close()

//            val file = File(context.getExternalFilesDir(null), PIC_FILE_NAME)
//            var output: FileOutputStream? = null
//            try {
//                output = FileOutputStream(file).apply {
//                    write(byteArray)
//                }
//            } catch (e: IOException) {
//                Log.e("Converter_error", e.toString())
//            }
            return fileFromBytes
        }
    }


}