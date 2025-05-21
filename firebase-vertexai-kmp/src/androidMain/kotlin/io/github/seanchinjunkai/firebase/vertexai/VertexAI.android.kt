package io.github.seanchinjunkai.firebase.vertexai

import com.google.firebase.Firebase
import com.google.firebase.vertexai.FirebaseVertexAI
import com.google.firebase.vertexai.vertexAI


actual class FirebaseW {
    actual companion object {
        actual fun vertexAI(): FirebaseVertexAIW {
            return FirebaseVertexAIW()
        }
    }

}

actual class FirebaseVertexAIW {
    private val delegate = Firebase.vertexAI()

    public actual fun generativeModel(modelName: String): GenerativeModelW {
        return GenerativeModelW(modelName, delegate)
    }
}



actual class GenerativeModelW(
    modelName: String,
    delegate: FirebaseVertexAI
) {
    private val delegate = delegate.generativeModel(modelName)

    public actual suspend fun generateContent(text: String): String {
        return delegate.generateContent(text).text ?: "No text"
    }
}