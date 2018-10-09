package br.com.paulosalvatore.ocean_android_a9_09_10_18

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import java.net.URL

class MainActivity : AppCompatActivity() {

	companion object {
		const val URL_IMAGEM = "https://img.global.news.samsung.com/br/wp-content/uploads/2017/08/20170814_195142.jpg"
		const val TAG_MAIN_ACTIVITY = "MainActivity"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		doAsync {
			// Executa ação em uma Thread separada

			runOnUiThread {
				// Executa na mainThread
			}
		}
	}

	fun workerThread(view: View) {
		toast("WorkerThread iniciada.")

		Thread(Runnable {
			val bitmap = carregarImagem(URL_IMAGEM)

			ivImagem.post {
				ivImagem.setImageBitmap(bitmap)
			}
		}).start()
	}

	fun asyncTask(view: View) {
		CarregarImagemTask().execute()
	}

	fun carregarImagem(urlImagem: String) : Bitmap? {
		try {
			val url = URL(urlImagem)

			val bitmap = BitmapFactory.decodeStream(
					url.openConnection().getInputStream()
			)

			return bitmap
		} catch (e: Exception) {
			Log.e(TAG_MAIN_ACTIVITY, "Erro ao carregar a imagem", e)
			return null
		}
	}

	inner class CarregarImagemTask : AsyncTask<Void, Void?, Bitmap?>() {
		override fun doInBackground(vararg voids: Void?): Bitmap? {
			return carregarImagem(URL_IMAGEM)
		}

		override fun onPostExecute(result: Bitmap?) {
			ivImagem.setImageBitmap(result)
		}

		override fun onPreExecute() {
			ivImagem.setImageResource(android.R.color.transparent)
		}
	}
}
