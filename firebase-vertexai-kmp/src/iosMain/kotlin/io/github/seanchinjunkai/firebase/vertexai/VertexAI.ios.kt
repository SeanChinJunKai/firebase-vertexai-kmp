package io.github.seanchinjunkai.firebase.vertexai
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import FirebaseVertexAIBridge.*
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class FirebaseW {
    actual companion object {
        actual fun vertexAI(): FirebaseVertexAIW {
            return FirebaseVertexAIW()
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseVertexAIW {
    private val delegate = FirebaseVertexAIBridge()

    public actual fun generativeModel(modelName: String): GenerativeModelW {
        return GenerativeModelW(modelName, delegate)
    }
}

@OptIn(ExperimentalForeignApi::class)
actual class GenerativeModelW(
    modelName: String,
    delegate: FirebaseVertexAIBridge
) {

    private val delegate = delegate.generativeModelWithModelName(modelName)

    public actual suspend fun generateContent(text: String): String = suspendCancellableCoroutine { continuation ->
        delegate.generateContentWithPrompt(
            text,
            completion = { result: String?, error: NSError? ->
                val string: String = result ?: "No result"
                when {
                    error != null -> continuation.resumeWithException(
                        Exception(
                            error.localizedDescription
                        )
                    )

                    result != null -> continuation.resume(string)
                    else -> continuation.resumeWithException(Exception("No result and no error returned."))
                }
            })
    }

}