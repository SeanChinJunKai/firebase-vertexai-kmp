package io.github.seanchinjunkai.firebase.ai

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.GenerativeModel as AndroidGenerativeModel
import com.google.firebase.ai.FirebaseAI as AndroidFirebaseAI


public actual object Firebase {
    // TODO: App parameter currently missing
    public actual fun ai(backend: GenerativeBackendEnum): FirebaseAI {
        return when (backend)  {
            GenerativeBackendEnum.GOOGLE_AI -> FirebaseAI(Firebase.ai(backend = GenerativeBackend.googleAI()))
            GenerativeBackendEnum.VERTEX_AI -> FirebaseAI(Firebase.ai(backend = GenerativeBackend.vertexAI()))
        }
    }
}



actual class FirebaseAI internal constructor(internal val androidFirebaseAI: AndroidFirebaseAI) {
    // TODO: Add missing parameters
    public actual fun generativeModel(modelName: String): GenerativeModel {
        return GenerativeModel(androidFirebaseAI.generativeModel(modelName))
    }
}


actual class GenerativeModel internal constructor(internal val androidGenerativeModel: AndroidGenerativeModel) {
    public actual suspend fun generateContent(prompt: String): String {
        return androidGenerativeModel.generateContent(prompt).text ?: "No content found"
    }
}
