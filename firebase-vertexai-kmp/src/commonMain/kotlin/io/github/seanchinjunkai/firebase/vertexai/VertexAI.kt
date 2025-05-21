package io.github.seanchinjunkai.firebase.vertexai

/*
Should be initialised like this

Firebase.ai(app, backend).generativeModel()

Firebase is a class when it does .ai() it creates an instance of FirebaseAI
which has generativeModel method



we need Firebase with a companion object function that returns FirebaseAI class

this class should have a generativeModel method
*/

expect class FirebaseW{
    companion object {
        fun vertexAI(): FirebaseVertexAIW
    }
}

expect class FirebaseVertexAIW {
    public fun generativeModel(modelName: String): GenerativeModelW
}

expect class GenerativeModelW {
    public suspend fun generateContent(text: String): String
}
