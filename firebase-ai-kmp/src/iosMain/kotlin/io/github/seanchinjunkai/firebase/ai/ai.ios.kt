package io.github.seanchinjunkai.firebase.ai
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import FirebaseAIBridge.*


@OptIn(ExperimentalForeignApi::class)
public actual object Firebase {
    // TODO: App parameter currently missing
    public actual fun ai(backend: GenerativeBackendEnum): FirebaseAI {
        return FirebaseAI(IOSFirebase.aiWithBackend(true))
    }
}

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseAI internal constructor(val iOSFirebaseAI: IOSFirebaseAI) {
    public actual fun generativeModel(modelName: String): GenerativeModel {
        return GenerativeModel(iOSFirebaseAI.generativeModelWithModelName(modelName))
    }
}

@OptIn(ExperimentalForeignApi::class)
actual class GenerativeModel internal constructor(val iOSGenerativeModel: IOSGenerativeModel) {
    public actual suspend fun generateContent(prompt: String): String = suspendCancellableCoroutine { continuation ->
        iOSGenerativeModel.generateContentWithPrompt(
            prompt,
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
